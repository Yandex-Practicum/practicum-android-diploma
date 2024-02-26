package ru.practicum.android.diploma.vacancy.presentation

import ru.practicum.android.diploma.core.domain.model.DetailVacancy

sealed class VacancyScreenState {
    data object Loading : VacancyScreenState()
    class Content(val vacancy: DetailVacancy) : VacancyScreenState()
    data object Error : VacancyScreenState()
}
