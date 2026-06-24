package ru.practicum.android.diploma.presentation.viewmodels

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoritesState {
    object Loading : FavoritesState
    data class Content(val vacancies: List<Vacancy>) : FavoritesState
    object Error : FavoritesState
    object Empty : FavoritesState
}
