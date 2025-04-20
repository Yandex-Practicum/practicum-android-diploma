package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.utils.StringProvider
import ru.practicum.android.diploma.domain.interactor.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.models.main.VacancyShort

class SearchViewModel(
    private val searchVacancyInteractor: SearchVacancyInteractor,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchState<List<VacancyShort>>())
    val searchState: StateFlow<SearchState<List<VacancyShort>>> = _searchState

    private fun searchVacancy(query: String) {
        viewModelScope.launch {
            _searchState.value = _searchState.value.copy(isLoading = true, query = query)

            searchVacancyInteractor.searchVacancy(query).collect { (content, error) ->
                _searchState.value = when {
                    content != null -> SearchState(
                        isLoading = false,
                        content = content,
                        query = query
                    )

                    error != null -> SearchState(
                        isLoading = false,
                        error = mapError(error),
                        query = query
                    )

                    else -> SearchState(
                        isLoading = false,
                        error = UiError.ServerError,
                        query = query
                    )
                }
            }
        }
    }

    private fun mapError(message: String?): UiError {
        return when {
            message?.contains(stringProvider.getString(R.string.errors_No_connection)) == true -> UiError.NoConnection
            message?.contains(stringProvider.getString(R.string.errors_bad_request)) == true -> UiError.BadRequest
            message?.contains(stringProvider.getString(R.string.errors_not_found)) == true -> UiError.NotFound
            message?.contains(stringProvider.getString(R.string.errors_Server)) == true -> UiError.ServerError
            else -> UiError.ServerError
        }
    }
}
