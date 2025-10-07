package ru.practicum.android.diploma.ui.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchState {
    object EmptyQuery : SearchState()
    object Loading : SearchState()
    object LoadingNextPage : SearchState()
    data class Success(
        val vacancies: List<Vacancy>,
        val found: Int,
        val isFirstPage: Boolean
    ) : SearchState()
    data class Error(val message: String) : SearchState()
    data class NextPageError(val message: String) : SearchState()
}
