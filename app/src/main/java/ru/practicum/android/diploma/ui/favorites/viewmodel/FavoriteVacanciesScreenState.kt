package ru.practicum.android.diploma.ui.favorites.viewmodel

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoriteVacanciesScreenState {

    data object Empty : FavoriteVacanciesScreenState

    data class Content(val favoriteVacancies: List<Vacancy>) : FavoriteVacanciesScreenState

    data object DbError : FavoriteVacanciesScreenState

}
