package ru.practicum.android.diploma.ui.favorites

import ru.practicum.android.diploma.domain.models.VacancyModel


sealed interface FavoritesState {
    object Loading : FavoritesState

    data class Content(
        val vacancies: List<VacancyModel>
    ) : FavoritesState

    data class Error(
        var error: ErrorCode
    ) : FavoritesState

    object Empty : FavoritesState

}

enum class ErrorCode { SOMETHING_WRONG }