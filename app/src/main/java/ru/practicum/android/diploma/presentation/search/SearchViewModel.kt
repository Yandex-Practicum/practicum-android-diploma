package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.utils.StringProvider
import ru.practicum.android.diploma.domain.interactor.SearchVacancyInteractor
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.main.VacancyShort

class SearchViewModel(
    private val searchVacancyInteractor: SearchVacancyInteractor,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState

    fun searchVacancy(query: String) {
        viewModelScope.launch {
            _searchState.value = _searchState.value.copy(
                isLoading = true,
                query = query
            )

            searchVacancyInteractor.searchVacancy(query).collect { resource ->
                _searchState.value = when (resource) {
                    is Resource.Success -> buildSuccessState(resource.data, query)
                    is Resource.Empty -> buildEmptyState(query)
                    is Resource.Error -> buildErrorState(resource.message, query)
                }
            }
        }
    }

    private fun buildSuccessState(
        content: List<VacancyShort>,
        query: String
    ): SearchState {
        val count = content.size
        val plural = stringProvider.getQuantityString(R.plurals.vacancies_count, count, count)
        val text = stringProvider.getString(R.string.results_Find) + " " + plural

        return SearchState(
            isLoading = false,
            content = content,
            resultCount = count,
            showResultText = true,
            resultText = text,
            query = query
        )
    }

    private fun buildEmptyState(query: String): SearchState {
        return SearchState(
            isLoading = false,
            error = UiError.BadRequest,
            resultText = stringProvider.getString(R.string.results_not_found),
            showResultText = true,
            query = query
        )
    }

    private fun buildErrorState(
        message: String,
        query: String
    ): SearchState {
        return SearchState(
            isLoading = false,
            error = mapError(message),
            query = query
        )
    }

    fun clearSearch() {
        _searchState.value = SearchState(
            content = emptyList(),
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
