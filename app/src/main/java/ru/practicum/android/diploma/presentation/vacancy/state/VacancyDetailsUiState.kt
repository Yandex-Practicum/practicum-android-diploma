package ru.practicum.android.diploma.presentation.vacancy.state

import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed interface VacancyDetailsUiState {
    data object Loading : VacancyDetailsUiState
    data object Error : VacancyDetailsUiState
    data object NotFound : VacancyDetailsUiState
    data object ServerError : VacancyDetailsUiState
    data class Content(val details: VacancyDetail) : VacancyDetailsUiState
}
