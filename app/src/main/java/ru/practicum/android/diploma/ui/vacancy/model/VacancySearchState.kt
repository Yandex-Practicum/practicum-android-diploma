package ru.practicum.android.diploma.ui.vacancy.model

import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

sealed interface VacancySearchState {
    data class Loading(
        val message: String
    ) : VacancySearchState

    data class Empty(
        val message: String
    ) : VacancySearchState

    data class Content(
        val vacancies: List<Vacancy>
    ) : VacancySearchState

    data class Error(
        val errorMessage: String
    ) : VacancySearchState
}
