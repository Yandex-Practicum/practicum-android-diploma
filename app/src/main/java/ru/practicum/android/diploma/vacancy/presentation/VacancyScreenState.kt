package ru.practicum.android.diploma.vacancy.presentation

import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

sealed interface VacancyScreenState {
    data class ContentState(val vacancy: Vacancy) : VacancyScreenState
    data object NetworkErrorState : VacancyScreenState
    data object LoadingState : VacancyScreenState
    data object EmptyState : VacancyScreenState
    data object ServerError : VacancyScreenState
    data object ConnectionError : VacancyScreenState
}
