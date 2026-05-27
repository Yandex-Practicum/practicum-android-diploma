package ru.practicum.android.diploma.favorites.ui

import ru.practicum.android.diploma.core.domain.models.Vacancies

sealed class FavoritesViewState {
    object Empty : FavoritesViewState()
    class Data(val vacancies: Vacancies) : FavoritesViewState()
    class Error : FavoritesViewState()
}
