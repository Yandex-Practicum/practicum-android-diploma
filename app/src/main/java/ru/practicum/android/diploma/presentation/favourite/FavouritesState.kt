package ru.practicum.android.diploma.presentation.favourite

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavouritesState {
    object Empty : FavouritesState
    object DbError : FavouritesState
    data class Content(val vacancies: List<Vacancy>) : FavouritesState
}
