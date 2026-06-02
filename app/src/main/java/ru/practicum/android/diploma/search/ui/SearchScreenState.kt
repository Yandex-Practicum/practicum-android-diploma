package ru.practicum.android.diploma.search.ui

import ru.practicum.android.diploma.core.domain.models.Vacancies

enum class SearchError {
    NO_INTERNET,
    SERVER_ERROR,
    EMPTY_RESULTS
}
sealed class SearchScreenState(var showClearButton: Boolean) {
    object Initial : SearchScreenState(false)
    object Loading : SearchScreenState(true)
    class Content(
        val vacancies: Vacancies,
        val totalFound: Int,
        val isNextPageLoading: Boolean
    ) : SearchScreenState(true)
    class Error(val error: SearchError) : SearchScreenState(true)
}
