package ru.practicum.android.diploma.ui.favorite

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed class FavoriteState {

    data object EmptyList : FavoriteState()

    data object Error : FavoriteState()

    data class VacancyList(
        val vacancies: List<VacancyDetails>
    ) : FavoriteState()
}
