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
import ru.practicum.android.diploma.domain.impl.SearchInteractor
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_MS

@OptIn(FlowPreview::class)
class JobSearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _state = MutableStateFlow<JobSearchState>(JobSearchState.Initial)
    val state: StateFlow<JobSearchState> = _state.asStateFlow()

    init {
        _searchQuery
            .debounce(SEARCH_DEBOUNCE_MS)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { query -> performSearch(query) }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _state.value = JobSearchState.Initial
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _state.value = JobSearchState.Initial
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _state.value = JobSearchState.Loading
            val result = searchInteractor.searchVacancies(query)
            _state.value = when {
                result != null && result.vacancies.isNotEmpty() -> {
                    JobSearchState.Content(
                        found = result.found,
                        vacancies = result.vacancies,
                    )
                }
                else -> JobSearchState.Initial
            }
        }
    }
}
