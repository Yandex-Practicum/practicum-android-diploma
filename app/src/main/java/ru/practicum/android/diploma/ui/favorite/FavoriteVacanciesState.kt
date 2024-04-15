package ru.practicum.android.diploma.ui.favorite

import ru.practicum.android.diploma.domain.models.VacancyDetails

sealed class FavoriteVacanciesState {

    data object EmptyVacancyList : FavoriteVacanciesState()

    data object VacanciesError : FavoriteVacanciesState()

    data class VacancyList(
        val vacancies: List<VacancyDetails>
    ) : FavoriteVacanciesState()
}
