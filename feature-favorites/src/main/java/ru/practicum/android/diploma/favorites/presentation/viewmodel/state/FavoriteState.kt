package ru.practicum.android.diploma.favorites.presentation.viewmodel.state

import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy

internal sealed interface FavoriteState {
    data class Content(
        val favoriteVacancies: List<FavoriteVacancy>
    ) : FavoriteState
    object Empty : FavoriteState
    object Error : FavoriteState
}
