package ru.practicum.android.diploma.presentation.favorite

import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

sealed interface FavoriteVacancyState {

    object Loading : FavoriteVacancyState

    object EmptyList : FavoriteVacancyState

    object Error : FavoriteVacancyState

    data class Content(
        val vacancy: List<VacancyDetail>
    ) : FavoriteVacancyState
}
