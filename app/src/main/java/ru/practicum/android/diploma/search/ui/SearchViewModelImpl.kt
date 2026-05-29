package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.util.NetworkConnectivity
import ru.practicum.android.diploma.search.domain.api.SearchInteractor

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModelImpl(
    private val searchInteractor: SearchInteractor,
    private val networkConnectivity: NetworkConnectivity
) : ViewModel(), SearchViewModel {

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    override val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()

    private val _isFiltered = MutableStateFlow(false)
    override val isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    override val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    private var currentPage = 0
    private var maxPages = 1
    private var isNextPageLoading = false
    private var currentTotalFound = 0
    private val vacanciesList = mutableListOf<Vacancy>()

    private data class SearchRequest(val query: String, val page: Int)
    private val searchTrigger = MutableSharedFlow<SearchRequest>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            _query
                .debounce(SEARCH_DEBOUNCE_DELAY)
                .distinctUntilChanged()
                .collect { q ->
                    if (q.isEmpty()) {
                        _state.value = SearchScreenState.Initial
                    } else {
                        searchTrigger.emit(SearchRequest(q, 0))
                    }
                }
        }

        viewModelScope.launch {
            searchTrigger
                .flatMapLatest { request ->
                    if (request.page == 0) {
                        currentPage = 0
                        maxPages = 1
                        vacanciesList.clear()
                        _state.value = SearchScreenState.Loading
                    } else {
                        isNextPageLoading = true
                        _state.value = SearchScreenState.Content(vacanciesList.toList(), currentTotalFound, true)
                    }

                    if (!networkConnectivity.isConnected()) {
                        flowOf(Resource.Error(-1) to request.page)
                    } else {
                        searchInteractor.searchVacancies(request.query, request.page, PER_PAGE, emptyMap())
                            .map { res -> res to request.page }
                    }
                }
                .collect { (resource, page) ->
                    when (resource) {
                        is Resource.Success -> {
                            maxPages = resource.data.pages
                            currentTotalFound = resource.data.found
                            val items = resource.data.items

                            if (page == 0 && items.isEmpty()) {
                                _state.value = SearchScreenState.EmptyResults
                            } else {
                                val uniqueItems = items.filter { newItem -> vacanciesList.none { it.id == newItem.id } }
                                vacanciesList.addAll(uniqueItems)
                                currentPage = page
                                isNextPageLoading = false
                                _state.value = SearchScreenState.Content(vacanciesList.toList(), currentTotalFound, false)
                            }
                        }
                        is Resource.Error -> {
                            isNextPageLoading = false

                            val isInternetError = resource.code == -1 || !networkConnectivity.isConnected()

                            if (page == 0) {
                                _state.value = if (isInternetError) SearchScreenState.NoInternet else SearchScreenState.ServerError
                            } else {
                                _state.value = SearchScreenState.Content(vacanciesList.toList(), currentTotalFound, false)
                                _toastMessage.emit(if (isInternetError) "Проверьте подключение к интернету" else "Произошла ошибка")
                            }
                        }
                        is Resource.Loading -> { }
                    }
                }
        }
    }

    override fun onQueryChanged(newText: String) {
        _query.value = newText
        if (newText.isEmpty()) {
            _state.value = SearchScreenState.Initial
        }
    }

    override fun onFocusChanged(hasFocus: Boolean) {}

    override fun onSearchIconClicked() {
        if (_query.value.isNotEmpty()) onQueryChanged("")
    }

    override fun onLastItemReached() {
        if (isNextPageLoading || currentPage >= maxPages - 1) return
        val currentQ = _query.value
        if (currentQ.isNotEmpty()) {
            searchTrigger.tryEmit(SearchRequest(currentQ, currentPage + 1))
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 300L
        private const val PER_PAGE = 20
    }
}
