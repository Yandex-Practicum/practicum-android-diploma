package ru.practicum.android.diploma.ui.models

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoritesState {
    data object Idle: FavoritesState
    data object Nothing: FavoritesState
    data object Loading: FavoritesState
    data class Complete(val data: List<Vacancy>): FavoritesState
    data object Error: FavoritesState
}
