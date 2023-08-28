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
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.delayedAction
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    logger: Logger
) : BaseViewModel(logger) {
    
    /*  init {
         viewModelScope.launch {
             searchVacanciesUseCase.search("nirvana")
         }
     } */
    
    private val _contentState = MutableStateFlow(SearchScreenState.Default)
    val contentState: StateFlow<SearchScreenState> = _contentState
    
    private var latestSearchQuery: String? = null
    private var searchJob: Job? = null
    
    private val onSearchDebaunce = delayedAction<String>(
        delayMillis = SEARCH_DELAY_MILLIS,
        coroutineScope = viewModelScope,
        action = { query ->
            loadJobList(query)
        })
    
    fun onSearchQueryChanged(query: String?) {
        if (query.isNullOrEmpty()) {
        
        }
    
    }
    
    private fun loadJobList(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchVacanciesUseCase.search(query)
        }
    }
    
    companion object {
        private const val SEARCH_DELAY_MILLIS = 2000L
    }
}