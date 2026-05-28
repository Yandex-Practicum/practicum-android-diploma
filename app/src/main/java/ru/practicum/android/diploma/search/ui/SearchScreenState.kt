package ru.practicum.android.diploma.search.ui

import ru.practicum.android.diploma.core.domain.models.Vacancy

sealed interface SearchScreenState {
    object Initial : SearchScreenState

    object Loading : SearchScreenState

    data class Content(
        val vacancies: List<Vacancy>,
        val totalFound: Int
    ) : SearchScreenState
}
