package ru.practicum.android.diploma.presentation.vacancy

import ru.practicum.android.diploma.domain.models.main.VacancyLong

sealed class VacancyState {
    data object Empty : VacancyState()

    data object Loading : VacancyState()

    data class Content(val vacancy: VacancyLong) : VacancyState()
}
