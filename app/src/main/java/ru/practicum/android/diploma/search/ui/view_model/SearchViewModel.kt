package ru.practicum.android.diploma.search.ui.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.search.domain.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.models.SearchUiState
import ru.practicum.android.diploma.util.delayedAction
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    logger: Logger
) : BaseViewModel(logger) {
    
    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState.Default)
    val uiState: StateFlow<SearchUiState> = _uiState
    
    private var latestSearchQuery: String? = null
    private var searchJob: Job? = null
    private var found: Int = 0
    private val onSearchDebounce = delayedAction<String>(
        coroutineScope = viewModelScope,
        action = { query ->
            searchVacancies(query)
        })
    
    fun onSearchQueryChanged(query: String) {
        log(thisName, "onSearchQueryChanged($query: String)")
        
        if (query == latestSearchQuery) return
        latestSearchQuery = query
        
        if (query.isEmpty()) {
            searchJob?.cancel()
            onSearchDebounce("")
            _uiState.value = SearchUiState.Default
        } else {
            onSearchDebounce(query)
        }
    }
    
    @NewResponse
    fun searchVacancies(query: String) {
        
        if (query.isNotEmpty()) {
            _uiState.value = SearchUiState.Loading
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                searchVacanciesUseCase
                    .searchVacancies(query)
                    .fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    @NewResponse
    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        _uiState.value = SearchUiState.Error(failure)
    }

    @NewResponse
    private fun handleSuccess(vacancies: Vacancies) {
        found = vacancies.found
        _uiState.value = SearchUiState.Content(list = vacancies.items, found = found)
    }
}