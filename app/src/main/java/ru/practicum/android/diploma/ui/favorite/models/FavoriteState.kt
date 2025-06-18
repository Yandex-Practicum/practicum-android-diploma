package ru.practicum.android.diploma.ui.favorite.models

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed interface FavoriteState {
    object Loading : FavoriteState
    object EmptyList : FavoriteState
    data class Error(
        val message: String
    ) : FavoriteState

    data class Context(
        val vacancies: List<VacancyDetails>
    ) : FavoriteState
}
