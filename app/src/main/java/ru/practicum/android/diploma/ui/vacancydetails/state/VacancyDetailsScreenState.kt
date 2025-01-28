package ru.practicum.android.diploma.ui.vacancydetails.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface VacancyDetailsScreenState {
    data object Loading : VacancyDetailsScreenState
    class Content(val vacancy: Vacancy) : VacancyDetailsScreenState
    data object ServerError : VacancyDetailsScreenState
    data object NotFoundError : VacancyDetailsScreenState
}
