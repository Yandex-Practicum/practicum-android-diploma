package ru.practicum.android.diploma.presentation.models.vacancies

import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

sealed class VacanciesState {
    object Loading : VacanciesState()
    data class Success(val vacancies: List<Vacancy>) : VacanciesState()
    object Empty : VacanciesState()
    object NoInternet : VacanciesState()
    object ServerError : VacanciesState()
}
