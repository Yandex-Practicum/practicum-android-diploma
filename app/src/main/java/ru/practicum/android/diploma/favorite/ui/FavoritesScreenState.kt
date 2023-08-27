package ru.practicum.android.diploma.favorite.ui

import ru.practicum.android.diploma.search.domain.Vacancy

sealed interface FavoritesScreenState {
    object Empty: FavoritesScreenState
    data class Content(val list: List<Vacancy>): FavoritesScreenState
}