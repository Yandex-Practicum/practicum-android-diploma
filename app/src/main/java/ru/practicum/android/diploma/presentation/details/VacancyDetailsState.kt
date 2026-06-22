package ru.practicum.android.diploma.presentation.details

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface VacancyDetailsState {
    object Loading : VacancyDetailsState
    data class Content(val vacancy: Vacancy) : VacancyDetailsState
    object Error : VacancyDetailsState
}
