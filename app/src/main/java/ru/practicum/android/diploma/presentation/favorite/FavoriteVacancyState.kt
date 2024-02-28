package ru.practicum.android.diploma.presentation.favorite

import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse

sealed interface FavoriteVacancyState {

    object Loading : FavoriteVacancyState

    object EmptyList : FavoriteVacancyState

    object Error : FavoriteVacancyState

    data class Content(
        val vacancy: List<VacancyDetailDtoResponse>
    ) : FavoriteVacancyState
}
