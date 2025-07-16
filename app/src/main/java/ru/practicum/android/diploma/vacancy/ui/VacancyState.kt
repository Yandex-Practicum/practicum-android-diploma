package ru.practicum.android.diploma.vacancy.ui

import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails

sealed interface VacancyState {
    object Loading : VacancyState
    data class Content(val vacancy: VacancyDetails, val isFavorite: Boolean) : VacancyState
    data class Error(val message: String) : VacancyState
}
