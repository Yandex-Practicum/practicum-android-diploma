package ru.practicum.android.diploma.vacancy.presentation.viewmodel

import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetail

sealed interface VacancyDetailState {
    object Loading : VacancyDetailState

    data class Content(val vacancy: VacancyDetail) : VacancyDetailState

    data class Error(val reason: String) : VacancyDetailState

    object Empty : VacancyDetailState
}
