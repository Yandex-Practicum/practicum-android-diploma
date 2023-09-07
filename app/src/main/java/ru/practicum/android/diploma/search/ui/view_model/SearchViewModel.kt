package ru.practicum.android.diploma.search.ui.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
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
    private var debounceJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + debounceJob)
    
    private val onSearchDebounce = delayedAction<String>(
        coroutineScope = coroutineScope,
//        viewModelScope,
        action = { query -> searchVacancies(query)
//            loadJobList(query)
        })

    fun onSearchQueryChanged(query: String) {
        log(thisName, "onSearchQueryChanged($query: String)")
        if (query.isEmpty()) {
            _uiState.value = SearchScreenState.Default
            debounceJob.cancel()
//            onSearchDebounce("")
            searchJob?.cancel()
        }
        else if (latestSearchQuery != query) {
            latestSearchQuery = query
            onSearchDebounce(query)
        }
    }

    @NewResponse
    private fun searchVacancies(query: String) {
        _uiState.value = SearchScreenState.Loading
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchVacanciesUseCase.searchVacancies(query)
                .fold(::handleFailure, ::handleSuccessVacancyResponse)
        }
    }

    @NewResponse
    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        _uiState.value = SearchScreenState.Error(failure)
    }

    @NewResponse
    private fun handleSuccessVacancyResponse(vacancies: Vacancies){
        log(thisName, "handleSuccessVacancyResponse vacancies found = (${vacancies.found} )")
        _uiState.value = SearchScreenState.Content(
            count = vacancies.found,
            jobList = vacancies.items,
        )
    }

//    private fun loadJobList(query: String) {
//        if(query.isEmpty()) return
//        log(thisName, "loadJobList($query: String)")
//        _uiState.value = SearchScreenState.Loading
//
//        searchJob = viewModelScope.launch(Dispatchers.IO) {
//            searchVacanciesUseCase
//                .search(query)
//                .collect { result ->
//                    processResult(result)
//                }
//        }
//    }
    
//    private fun processResult(result: FetchResult) {
//        log(thisName, "processResult(${result.thisName}: FetchResult)")
//        when {
//            result.error != null -> {
////                _uiState.value = SearchScreenState.Error(result.error)
//            }
//            result.data != null -> {
////                _uiState.value = SearchScreenState.Content(
////                    count = result.count ?: 0,
////                    jobList = result.data,
////                )
//            }
//        }
//    }
}