package ru.practicum.android.diploma.favourites.ui

import ru.practicum.android.diploma.search.domain.models.Vacancy

sealed class FavouritesState {
    data object Loading : FavouritesState()
    data class Content(val favouritesList: List<Vacancy>) : FavouritesState()
    data object Error : FavouritesState()
}
