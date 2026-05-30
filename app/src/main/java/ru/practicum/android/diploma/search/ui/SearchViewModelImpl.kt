package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.search.domain.api.SearchInteractor

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModelImpl(
    private val searchInteractor: SearchInteractor
) : SearchViewModel() {
    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Default)
    override var state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()
    private val _isFiltered = MutableStateFlow<Boolean>(false)
    override var isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()

    private data class SearchRequest(val query: String, val filters: Filters, val page: Int)

    init {
        viewModelScope.launch {
            combine<String, Filters, SearchRequest?>(
                _query
                    .debounce(SEARCH_DEBOUNCE_DELAY)
                    .distinctUntilChanged(),
                searchInteractor.filters
            ) { query, filters ->
                _isFiltered.value = filters.salary != null ||
                    filters.area != null ||
                    filters.industry != null ||
                    filters.onlyWithSalary

                if (query.isEmpty() && !_isFiltered.value) {
                    _state.value = SearchScreenState.Default
                    null
                } else {
                    SearchRequest(query, filters, 0)
                }
            }
                .collect { request ->
                    request?.let {
                        searchInteractor.searchVacancies(
                            request.query,
                            request.page,
                            20,
                            request.filters
                        )
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

        }
    }

    override fun onQueryChanged(query: String) {
        _query.value = query
        if (query.isEmpty()) {
            _state.value = SearchScreenState.Default
        }
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
