package ru.practicum.android.diploma.vacancy.presentation.viewmodel.state

import ru.practicum.android.diploma.vacancy.domain.model.Vacancy

internal sealed interface VacancyDetailState {
    data object Loading : VacancyDetailState
    data class Content(val vacancy: Vacancy) : VacancyDetailState
    data class Error(val reason: String) : VacancyDetailState
    data object Empty : VacancyDetailState
}
