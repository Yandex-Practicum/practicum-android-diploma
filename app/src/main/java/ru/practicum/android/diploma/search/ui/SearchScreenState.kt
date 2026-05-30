package ru.practicum.android.diploma.search.ui

import ru.practicum.android.diploma.core.domain.models.Vacancies

enum class SearchError {
    INTERNET,
    NOT_FOUND
}
sealed class SearchScreenState(var showClearButton: Boolean) {
    object Default : SearchScreenState(false)
    object Loading : SearchScreenState(true)
    class Content(
        val vacancies: Vacancies,
        val totalFound: Int
    ) : SearchScreenState(true)
    class Error(val error: SearchError) : SearchScreenState(true)
}
