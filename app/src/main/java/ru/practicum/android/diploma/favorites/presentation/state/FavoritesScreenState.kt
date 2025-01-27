package ru.practicum.android.diploma.favorites.presentation.state

import ru.practicum.android.diploma.search.domain.model.VacancyItems

sealed interface FavoritesScreenState {
    data class Content(val data: List<VacancyItems>) : FavoritesScreenState
    data class Error(val message: String) : FavoritesScreenState
}
