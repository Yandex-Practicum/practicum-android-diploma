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
                val count = content?.size ?: 0
                val pluralForm = if (count > 0) {
                    stringProvider.getQuantityString(R.plurals.vacancies_count, count, count)
                } else ""
                val resultText = stringProvider.getString(R.string.results_Find) + " " + pluralForm
                _searchState.value = when {
                    !content.isNullOrEmpty() -> SearchState(
                        isLoading = false,
                        content = content,
                        query = query,
                        resultCount = count,
                        showResultText = count > 0,
                        resultText = resultText
                    )

                    content != null && content.isEmpty() -> SearchState(
                        isLoading = false,
                        error = UiError.BadRequest,
                        resultText = stringProvider.getString(R.string.results_not_found),
                        showResultText = true,
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
                        error = UiError.BadRequest,
                        query = query
                    )
                }
                Log.d("SearchVM", "content: $content or error: $error")
                Log.d(
                    "SearchVM",
                    "resultCount=${count}, resultText=$resultText, contentSize=${content?.size}"
                )
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
            message?.contains(stringProvider.getString(R.string.errors_Server)) == true -> UiError.ServerError
            else -> UiError.ServerError
        }
    }
}
