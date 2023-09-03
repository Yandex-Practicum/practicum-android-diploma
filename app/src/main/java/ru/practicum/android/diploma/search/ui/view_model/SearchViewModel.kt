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
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.delayedAction
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
    
    private val onSearchDebounce = delayedAction<String>(
        coroutineScope = viewModelScope,
        action = { query -> loadJobList(query) })
    
    fun onSearchQueryChanged(query: String?) {
        log(thisName, "onSearchQueryChanged -> $query")
        if (query.isNullOrEmpty()) {
            _uiState.value = SearchScreenState.Default
        } else {
            if (latestSearchQuery == query) return
            log(thisName, "latestSearchQuery -> $latestSearchQuery")
            latestSearchQuery = query
            onSearchDebounce(query)
        }
    }
    
    private fun loadJobList(query: String) {
        log(thisName, "loadJobList -> $query")
        _uiState.value = SearchScreenState.Loading
    
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchVacanciesUseCase
                .search(query)
                .collect { result ->
                    processResult(result)
                }
        }
    }
    
    private fun processResult(result: FetchResult) {
        log(thisName, "processResult: $result")
        when {
            result.error != null -> {
                _uiState.value = SearchScreenState.Error(result.error)
            }
            
            result.data != null -> {
                _uiState.value = SearchScreenState.Content(
                    count = result.count ?: 0,
                    jobList = result.data,
                )
            }
        }
    }
}