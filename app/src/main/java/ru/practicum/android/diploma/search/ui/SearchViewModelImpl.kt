package ru.practicum.android.diploma.search.ui

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.api.SearchInteractor

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModelImpl(
    private val searchInteractor: SearchInteractor
) : SearchViewModel() {
    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    override var state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()
    private val _isFiltered = MutableStateFlow<Boolean>(false)
    override var isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()

    private val _errorCode = MutableSharedFlow<SearchError?>()
    override val errorCode: SharedFlow<SearchError?> = _errorCode.asSharedFlow()

    private val filters = MutableStateFlow<Filters>(Filters())

    private var currentPage = 0
    private var maxPages = 1
    private var isNextPageLoading = false
    private var currentTotalFound = 0
    private val vacanciesList = mutableListOf<Vacancy>()

    private data class SearchRequest(val query: String, val filters: Filters, val page: Int)
    private val searchTrigger = MutableSharedFlow<SearchRequest>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            combine<String, Filters, SearchRequest?>(
                _query
                    .debounce(SEARCH_DEBOUNCE_DELAY)
                    .distinctUntilChanged(),
                searchInteractor.filters,
            ) { query, filters ->
                this@SearchViewModelImpl.filters.value = filters

                _isFiltered.value = filters.salary != null ||
                    filters.area != null ||
                    filters.industry != null ||
                    filters.onlyWithSalary

                if (query.isEmpty() && !_isFiltered.value) {
                    _state.value = SearchScreenState.Initial
                    null
                } else {
                    SearchRequest(query, filters, 0)
                }
            }
                .collect { request ->
                    request?.let {
                        searchTrigger.emit(request)
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
                        _state.value = SearchScreenState.Content(vacanciesList.toList(), currentTotalFound, true,)
                    }

                    searchInteractor.searchVacancies(request.query, request.page, PER_PAGE, request.filters)
                        .map { res -> res to request.page }
                }.collect { (resource, page) ->
                    isNextPageLoading = false
                    when (resource) {
                        is Resource.Success -> {
                            maxPages = resource.data.pages
                            currentTotalFound = resource.data.found
                            val items = resource.data.items
                            if (page == 0 && items.isEmpty()) {
                                _state.value = SearchScreenState.Error(SearchError.EMPTY_RESULTS)
                            } else {
                                val uniqueItems = items.filter { newItem -> vacanciesList.none { it.id == newItem.id } }
                                vacanciesList.addAll(uniqueItems)
                                currentPage = page

                                _state.value =
                                    SearchScreenState.Content(vacanciesList.toList(), currentTotalFound, false,)
                            }
                        }
                        is Resource.Error -> {
                            val error = if (resource.code == ResultCode.NO_INTERNET) {
                                SearchError.NO_INTERNET
                            } else {
                                SearchError.SERVER_ERROR
                            }
                            if (page == 0) {
                                _state.value = SearchScreenState.Error(error)
                            } else {
                                _state.value =
                                    SearchScreenState.Content(vacanciesList.toList(), currentTotalFound, false,)
                                _errorCode.emit(error)
                            }
                        }
                        is Resource.Loading -> {
                            if (page == 0) {
                                _state.value = SearchScreenState.Loading
                            }
                        }
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
    override fun onSearchIconClicked() {
        if (_query.value.isNotEmpty()) {
            onQueryChanged("")
        }
    }

    override fun onLastItemReached() {
        if (isNextPageLoading || currentPage >= maxPages - 1) return
        val currentQ = _query.value
        if (currentQ.isNotEmpty() || _isFiltered.value) {
            searchTrigger.tryEmit(SearchRequest(currentQ, filters.value, currentPage + 1))
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 300L
        private const val PER_PAGE = 20
    }
}
