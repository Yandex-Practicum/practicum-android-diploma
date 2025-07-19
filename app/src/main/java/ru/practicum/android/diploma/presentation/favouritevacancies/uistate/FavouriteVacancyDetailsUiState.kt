package ru.practicum.android.diploma.presentation.favouritevacancies.uistate

import ru.practicum.android.diploma.domain.models.vacancydetails.VacancyDetails

sealed class FavouriteVacancyDetailsUiState {
    data class Content(val data: VacancyDetails) : FavouriteVacancyDetailsUiState()
    data object Error : FavouriteVacancyDetailsUiState()
}
