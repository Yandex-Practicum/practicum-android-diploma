package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.search.domain.models.Vacancy

sealed interface SearchState {
    object Loading : SearchState
    data class VacancyContent(
        val vacancies: List<Vacancy>,
        val foundValue: Int,
    ) : SearchState

    data class Error(
        val errorMessage: String,
    ) : SearchState

    data class Empty(
        val message: String,
    ) : SearchState
}