package ru.practicum.android.diploma.vacancy.ui

import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

sealed class VacancyDetailsViewState {
    object Loading : VacancyDetailsViewState()
    data class Data(val details: VacancyDetails) : VacancyDetailsViewState()
    object Error : VacancyDetailsViewState()
}
