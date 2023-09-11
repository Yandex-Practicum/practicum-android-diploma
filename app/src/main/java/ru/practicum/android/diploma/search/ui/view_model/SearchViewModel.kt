package ru.practicum.android.diploma.search.ui.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.models.SearchUiState
import ru.practicum.android.diploma.util.delayedAction
import ru.practicum.android.diploma.util.functional.Failure
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    logger: Logger
) : BaseViewModel(logger) {
    
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.Default)
    val uiState: StateFlow<SearchUiState> = _uiState
    
    private var latestSearchQuery: String? = null
    private var vacancyList: List<Vacancy> = emptyList()
    private var searchJob: Job? = null
    private var isNextPageLoading: Boolean = false
    private var found: Int = 0
    private var maxPages: Int = 0
    private var currentPage: Int = 0
    
    private val onSearchDebounce =
        delayedAction<String>(coroutineScope = viewModelScope, action = { query ->
            searchVacancies(query = query, isFirstPage = currentPage == FIRST_PAGE)
        })
    
    fun onSearchQueryChanged(query: String) {
        
        if (query == latestSearchQuery) return
        latestSearchQuery = query
        currentPage = FIRST_PAGE
        
        if (query.isEmpty()) {
            searchJob?.cancel()
            onSearchDebounce("")
            _uiState.value = SearchUiState.Default
        } else {
            onSearchDebounce(query)
        }
    }
    
    fun searchVacancies(query: String, isFirstPage: Boolean) {
        if (query.isNotEmpty()) {
            if (isFirstPage) _uiState.value = SearchUiState.Loading
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                searchVacanciesUseCase(query, currentPage++).fold(::handleFailure, ::handleSuccess)
            }
        }
    }
    
    fun onScrolledBottom() {
        if (!isNextPageLoading && currentPage < maxPages) {
            isNextPageLoading = true
            latestSearchQuery?.let {
                searchVacancies(
                    query = it, isFirstPage = currentPage == FIRST_PAGE
                )
            }
        }
    }
    
    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        if (currentPage == maxPages) _uiState.value = SearchUiState.Error(failure)
        else _uiState.value = SearchUiState.ErrorScrollLoading(failure)
        isNextPageLoading = false
    }
    
    private fun handleSuccess(vacancies: Vacancies) {
        maxPages = vacancies.pages
        found = vacancies.found
        vacancyList += vacancies.items
        
        _uiState.value = SearchUiState.Content(
            list = vacancyList, found = vacancies.found, isLastPage = currentPage >= maxPages
        )
        isNextPageLoading = false
    }
    
    companion object {
        private const val FIRST_PAGE = 0
    }
}
