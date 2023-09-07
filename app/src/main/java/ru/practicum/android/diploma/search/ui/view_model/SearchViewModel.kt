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
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.delayedAction
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    logger: Logger
) : BaseViewModel(logger) {
    private val _uiState: MutableStateFlow<SearchScreenState> =
        MutableStateFlow(SearchScreenState.Default)
    val uiState: StateFlow<SearchScreenState> = _uiState
    private var latestSearchQuery: String? = null
    private var searchJob: Job? = null
    private val currentVacancyList = mutableListOf <Vacancy>()
    private val onSearchDebounce = delayedAction<String>(
        coroutineScope = viewModelScope,
        action = { query ->
            searchVacancies(query)
        })

    fun onSearchQueryChanged(query: String) {
        log(thisName, "onSearchQueryChanged($query: String)")
        _uiState.value = SearchScreenState.Default
        if (latestSearchQuery != query) {
            latestSearchQuery = query
            onSearchDebounce(query)
        }
    }

    fun onResume(){
        if (currentVacancyList.isNotEmpty()){
            _uiState.value = SearchScreenState.Content(
                found = 0,
                jobList = currentVacancyList
            )
        }
    }

    @NewResponse
    private fun searchVacancies(query: String) {
        searchJob?.cancel()
        if (query.isNotEmpty()) {
            _uiState.value = SearchScreenState.Loading
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                searchVacanciesUseCase.searchVacancies(query)
                    .fold(::handleFailure, ::handleSuccess)
            }
        }
    }

    @NewResponse
    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        _uiState.value = SearchScreenState.Error(failure)
    }

    @NewResponse
    private fun handleSuccess(vacancies: Vacancies) {
        log(thisName, "handleSuccessVacancyResponse vacancies found = (${vacancies.found} )")
        _uiState.value = SearchScreenState.Content(
            found = vacancies.found,
            jobList = vacancies.items
        )
        currentVacancyList.clear()
        currentVacancyList.addAll(vacancies.items)
    }
}