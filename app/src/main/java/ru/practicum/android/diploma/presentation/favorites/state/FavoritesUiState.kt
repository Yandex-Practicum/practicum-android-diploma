package ru.practicum.android.diploma.presentation.favorites.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState

    data object Empty : FavoritesUiState

    data object Error : FavoritesUiState

    data class Content(val vacancies: List<Vacancy>) : FavoritesUiState
}
