package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface VacancyState {

    data object Loading : VacancyState

    data class Content(
        val item: Vacancy,
    ) : VacancyState

    data object Empty : VacancyState

    data object NetworkError : VacancyState

    data object BadRequest : VacancyState

    data object ServerError : VacancyState

}
