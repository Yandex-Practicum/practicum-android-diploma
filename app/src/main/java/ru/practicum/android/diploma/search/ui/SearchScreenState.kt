package ru.practicum.android.diploma.search.ui

import ru.practicum.android.diploma.core.domain.models.Vacancy

enum class SearchError {
    INTERNET,
    NOT_FOUND
}

sealed interface SearchScreenState {
    object Initial : SearchScreenState

    object Loading : SearchScreenState

    data class Content(
        val vacancies: List<Vacancy>,
        val totalFound: Int,
        val isNextPageLoading: Boolean
    ) : SearchScreenState

    object NoInternet : SearchScreenState
    object ServerError : SearchScreenState
    object EmptyResults : SearchScreenState
}
