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
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    logger: Logger
) : BaseViewModel(logger) {
    
    private val _contentState: MutableStateFlow<SearchScreenState> =
        MutableStateFlow(SearchScreenState.Default)
    val contentState: StateFlow<SearchScreenState> = _contentState
    
    private var latestSearchQuery: String? = null
    private var searchJob: Job? = null
    
    private val onSearchDebounce = delayedAction<String>(delayMillis = SEARCH_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        action = { query -> loadJobList(query) })
    
    fun onSearchQueryChanged(query: String?) {
        if (query.isNullOrEmpty()) {
            _contentState.value = SearchScreenState.Default
        } else {
            if (latestSearchQuery == query) return
        
            latestSearchQuery = query
            onSearchDebounce(query)
        }
    }
    
    private fun loadJobList(query: String) {
    
        _contentState.value = SearchScreenState.Loading
    
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchVacanciesUseCase
                .search(query)
                .collect { result ->
                    processResult(result)
                }
        }
    }
    
    private fun processResult(result: FetchResult) {
        when {
            result.error != null -> {
                _contentState.value = SearchScreenState.Error(result.error)
            }
            
            result.data != null -> {
                _contentState.value = SearchScreenState.Content(result.data)
            }
        }
    }
    
    companion object {
        private const val SEARCH_DELAY_MILLIS = 2000L
    }
}