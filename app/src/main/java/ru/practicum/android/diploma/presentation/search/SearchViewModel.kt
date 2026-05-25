package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.practicum.android.diploma.domain.interactors.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancySearchRequest
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val searchDebounce = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_MILLIS,
        coroutineScope = viewModelScope,
        useLastParam = true,
    ) { query ->
        searchVacancies(query)
    }

    fun onQueryChanged(query: String) {
        _uiState.update { state ->
            state.copy(
                query = query,
                isLoading = false,
                found = 0,
                vacancies = emptyList(),
            )
        }

        if (query.isBlank()) {
            searchDebounce.cancel()
            return
        }

        searchDebounce(query)
    }

    fun onClearQueryClicked() {
        onQueryChanged(query = "")
    }

    private suspend fun searchVacancies(query: String) {
        _uiState.update { state -> state.copy(isLoading = true) }

        when (val result = vacanciesInteractor.searchVacancies(VacancySearchRequest(text = query))) {
            is Resource.Success -> {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        found = result.data.found,
                        vacancies = result.data.vacancies,
                    )
                }
            }

            is Resource.Error -> {
                _uiState.update { state -> state.copy(isLoading = false) }
            }

            Resource.Loading -> Unit
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MILLIS = 2_000L
    }
}
