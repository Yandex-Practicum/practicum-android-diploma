package ru.practicum.android.diploma.presentation.favouritevacancies.uistate

import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel

sealed class FavouriteVacanciesUiState {
    data class Content(val vacancies: List<VacancyUiModel>): FavouriteVacanciesUiState()
    data class Placeholder(val drawable: Int, val message: Int): FavouriteVacanciesUiState()
}
