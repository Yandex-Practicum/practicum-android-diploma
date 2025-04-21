package ru.practicum.android.diploma.presentation.search

import android.util.Log
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

    fun searchVacancy(query: String) {
        viewModelScope.launch {
            _searchState.value =
                _searchState.value.copy(isLoading = true, noContent = true, query = query)

            searchVacancyInteractor.searchVacancy(query).collect { (content, error) ->
                _searchState.value = when {
                    !content.isNullOrEmpty() -> SearchState(
                        isLoading = false,
                        content = content,
                        query = query
                    )

                    content != null && content.isEmpty() -> SearchState(
                        isLoading = false,
                        content = emptyList(),
                        noContent = true,
                        query = query
                    )

                    error != null -> SearchState(
                        isLoading = false,
                        error = mapError(error),
                        query = query
                    )

                    else -> SearchState(
                        isLoading = false,
                        error = UiError.NotFound,
                        query = query
                    )
                }
                Log.d("SearchVM", "data: $error")
            }
        }
    }

    fun clearSearch() {
        _searchState.value = SearchState(
            content = emptyList(),
            noContent = true
        )
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
