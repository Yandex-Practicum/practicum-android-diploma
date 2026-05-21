package ru.practicum.android.diploma.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.impl.SearchInteractor
import ru.practicum.android.diploma.domain.models.SearchVacanciesOutcome
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_MS

@OptIn(FlowPreview::class)
class JobSearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow<JobSearchState>(JobSearchState.Initial)
    val state: StateFlow<JobSearchState> = _state.asStateFlow()

    private val _vacancies = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacancies: StateFlow<List<Vacancy>> = _vacancies.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _maxPages = MutableStateFlow(0)
    val maxPages: StateFlow<Int> = _maxPages.asStateFlow()

    private val _isNextPageLoading = MutableStateFlow(false)
    val isNextPageLoading: StateFlow<Boolean> = _isNextPageLoading.asStateFlow()

    init {
        _searchQuery
            .debounce(SEARCH_DEBOUNCE_MS)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { query -> performSearch(query, page = 0) }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            resetSearchState()
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        resetSearchState()
    }

    fun loadNextPage() {
        val query = _searchQuery.value.trim()
        if (query.isEmpty() || _isNextPageLoading.value) {
            return
        }
        val nextPage = _currentPage.value + 1
        if (nextPage >= _maxPages.value) {
            return
        }
        viewModelScope.launch {
            _isNextPageLoading.value = true
            when (val outcome = searchInteractor.searchVacancies(query, nextPage)) {
                is SearchVacanciesOutcome.Success -> {
                    _currentPage.value = outcome.result.page
                    _vacancies.update { current -> current + outcome.result.vacancies }
                }
                is SearchVacanciesOutcome.Empty,
                is SearchVacanciesOutcome.Error,
                -> Unit
            }
            _isNextPageLoading.value = false
        }
    }

    private fun performSearch(query: String, page: Int) {
        viewModelScope.launch {
            resetPagination()
            _state.value = JobSearchState.Loading
            when (val outcome = searchInteractor.searchVacancies(query, page)) {
                is SearchVacanciesOutcome.Success -> applySuccess(outcome, replaceList = true)
                SearchVacanciesOutcome.Empty -> _state.value = JobSearchState.Empty
                SearchVacanciesOutcome.Error -> _state.value = JobSearchState.Error
            }
        }
    }

    private fun applySuccess(outcome: SearchVacanciesOutcome.Success, replaceList: Boolean) {
        _currentPage.value = outcome.result.page
        _maxPages.value = outcome.result.pages
        _vacancies.value = if (replaceList) {
            outcome.result.vacancies
        } else {
            _vacancies.value + outcome.result.vacancies
        }
        _state.value = JobSearchState.Content(found = outcome.result.found)
    }

    private fun resetPagination() {
        _vacancies.value = emptyList()
        _currentPage.value = 0
        _maxPages.value = 0
        _isNextPageLoading.value = false
    }

    private fun resetSearchState() {
        resetPagination()
        _state.value = JobSearchState.Initial
    }
}
