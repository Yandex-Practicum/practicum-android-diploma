package ru.practicum.android.diploma.ui.favorites

import ru.practicum.android.diploma.domain.model.VacancyModel

sealed interface FavoritesState {
    object Loading : FavoritesState

    data class Content(
        val vacancies: List<VacancyModel>
    ) : FavoritesState

    object Error : FavoritesState

    object Empty : FavoritesState

}
