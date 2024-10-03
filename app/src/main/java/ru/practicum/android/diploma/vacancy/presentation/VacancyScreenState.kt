package ru.practicum.android.diploma.vacancy.presentation

import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

sealed interface VacancyScreenState {
    data class Content(val vacancy: Vacancy) : VacancyScreenState
    object Error : VacancyScreenState
}
