package ru.practicum.android.diploma.presentation.vacancydetailsscreen.uistate

import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails

sealed class VacancyDetailsUiState {
    data object Loading : VacancyDetailsUiState()
    data class Content(val detailsVacancy: VacancyDetails) : VacancyDetailsUiState()
    data object NothingFound : VacancyDetailsUiState()
    data object ServerError : VacancyDetailsUiState()
}
