package ru.practicum.android.diploma.presentation.favorites

import ru.practicum.android.diploma.domain.models.VacanciesPreview

sealed class FavoritesScreenState {
    data class Content(
        val vacancies: List<VacanciesPreview>,
    ) : FavoritesScreenState()

    data object Empty : FavoritesScreenState()
    data object Error : FavoritesScreenState()
}
