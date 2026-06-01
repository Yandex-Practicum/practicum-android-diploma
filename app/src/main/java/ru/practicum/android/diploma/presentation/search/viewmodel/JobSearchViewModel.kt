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
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.models.SearchVacanciesOutcome
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.search.state.JobSearchState
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_MS

@OptIn(FlowPreview::class)
class JobSearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow<JobSearchState>(JobSearchState.Initial)
    val state: StateFlow<JobSearchState> = _state.asStateFlow()

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private var currentPage = 0
    private var maxPages = 0

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
        val content = _state.value as? JobSearchState.Content ?: return
        val nextPage = currentPage + 1
        if (!canLoadNextPage(content, query, nextPage)) {
            return
        }
        viewModelScope.launch {
            _state.value = content.copy(isLoading = true)
            when (val outcome = searchInteractor.searchVacancies(query, nextPage)) {
                is SearchVacanciesOutcome.Success -> {
                    currentPage = outcome.result.page
                    _state.value = JobSearchState.Content(
                        found = outcome.result.found,
                        vacancies = content.vacancies + outcome.result.vacancies,
                        isLoading = false,
                    )
                }
                is SearchVacanciesOutcome.Empty,
                is SearchVacanciesOutcome.Error,
                -> stopPaginationLoading()
            }
        }
    }

    private fun canLoadNextPage(
        content: JobSearchState.Content,
        query: String,
        nextPage: Int,
    ): Boolean {
        if (query.isEmpty()) {
            return false
        }
        return !content.isLoading && nextPage < maxPages
    }

    private fun stopPaginationLoading() {
        val currentContent = _state.value as? JobSearchState.Content ?: return
        _state.value = currentContent.copy(isLoading = false)
    }

    private fun performSearch(query: String, page: Int) {
        viewModelScope.launch {
            resetPagination()
            _state.value = JobSearchState.Content(
                found = 0,
                vacancies = emptyList(),
                isLoading = true,
            )
            when (val outcome = searchInteractor.searchVacancies(query, page)) {
                is SearchVacanciesOutcome.Success -> applySuccess(outcome, replaceList = true)
                SearchVacanciesOutcome.Empty -> _state.value = JobSearchState.Empty
                SearchVacanciesOutcome.Error -> _state.value = JobSearchState.Error
            }
        }
    }

    private fun applySuccess(outcome: SearchVacanciesOutcome.Success, replaceList: Boolean) {
        currentPage = outcome.result.page
        maxPages = outcome.result.pages
        val currentContent = _state.value as? JobSearchState.Content
        val vacancies = if (replaceList) {
            outcome.result.vacancies
        } else {
            currentContent?.vacancies.orEmpty() + outcome.result.vacancies
        }
        _state.value = JobSearchState.Content(
            found = outcome.result.found,
            vacancies = vacancies,
            isLoading = false,
        )
    }

    private fun resetPagination() {
        currentPage = 0
        maxPages = 0
    }

    private fun resetSearchState() {
        resetPagination()
        _state.value = JobSearchState.Initial
    }

    data class SearchState(
        val jobList: List<Vacancy> = emptyList(),
        val selectedJob: Vacancy? = null,
        val errorVisible: Boolean = false,
        val recyclerVisible: Boolean = false,
        val progressBarVisible: Boolean = false,
        val errorText: String = "",
        val errorIcon: Int = 0,
    )
}
