package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class VacancyState {
    object Initial : VacancyState()
    object Loading : VacancyState()
    data class Content(val vacancy: Vacancy) : VacancyState()
    object Error : VacancyState()
    object Empty : VacancyState()
}
