package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.commons.domain.model.DetailVacancy

sealed interface VacancyState {
    data object Loading : VacancyState
    data class Content(val vacancy: DetailVacancy) : VacancyState
    data object Error : VacancyState
    data object EmptyScreen : VacancyState

}
