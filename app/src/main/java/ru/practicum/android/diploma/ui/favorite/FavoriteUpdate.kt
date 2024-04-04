package ru.practicum.android.diploma.ui.favorite

import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

sealed class FavoriteUpdate {

    data object EmptyVacancyList : FavoriteUpdate()

    data object GetVacanciesError : FavoriteUpdate()

    data class VacancyList(
        val vacancies: List<Vacancy>
    ) : FavoriteUpdate()
}
