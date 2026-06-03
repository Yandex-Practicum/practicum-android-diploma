package ru.practicum.android.diploma.vacancy.ui

import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

sealed interface VacancyState {
    object Loading : VacancyState
    data class Content(
        val details: VacancyDetails,
        val isFavorite: Boolean,
        val fromCache: Boolean = false,
    ) : VacancyState
    object NoInternet : VacancyState
    object Error : VacancyState
    object NotFound : VacancyState
}
