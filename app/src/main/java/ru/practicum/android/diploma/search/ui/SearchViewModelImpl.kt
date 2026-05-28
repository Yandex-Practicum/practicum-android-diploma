package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.search.domain.api.SearchInteractor

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModelImpl(
    private val searchInteractor: SearchInteractor
) : ViewModel(), SearchViewModel {

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    override val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()

    private val _isFiltered = MutableStateFlow(false)
    override val isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()

    init {
        viewModelScope.launch {
            _query
                .debounce(SEARCH_DEBOUNCE_DELAY)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest { currentQuery ->
                    _state.value = SearchScreenState.Loading
                    searchInteractor.searchVacancies(
                        query = currentQuery,
                        page = 0,
                        perPage = 20,
                        filters = emptyMap()
                    )
                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _state.value = SearchScreenState.Content(
                                vacancies = resource.data.items,
                                totalFound = resource.data.found
                            )
                        }
                        is Resource.Error -> {
                            _state.value = SearchScreenState.Error(SearchError.INTERNET)
                        }
                        is Resource.Loading -> {
                            _state.value = SearchScreenState.Loading
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

    override fun onFocusChanged(hasFocus: Boolean) {
    }

    override fun onSearchIconClicked() {
        if (_query.value.isNotEmpty()) {
            onQueryChanged("")
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 300L
    }
}
