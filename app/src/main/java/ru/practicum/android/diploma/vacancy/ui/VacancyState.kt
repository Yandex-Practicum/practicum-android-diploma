package ru.practicum.android.diploma.vacancy.ui

import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

sealed class VacancyState {
    data object Loading : VacancyState()
    data class Content(val vacancyFull: VacancyFull) : VacancyState()
    data object ErrorVacancyNotFound : VacancyState()
    data object ErrorServer : VacancyState()
}
