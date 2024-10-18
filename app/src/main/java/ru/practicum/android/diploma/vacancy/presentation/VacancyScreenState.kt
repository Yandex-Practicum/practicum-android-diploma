package ru.practicum.android.diploma.vacancy.presentation

import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

sealed class VacancyScreenState {
    data class ContentState(val vacancy: Vacancy) : VacancyScreenState()
    object NetworkErrorState : VacancyScreenState()
    object ServerError : VacancyScreenState()
    object LoadingState : VacancyScreenState()
    object EmptyState : VacancyScreenState()
}
