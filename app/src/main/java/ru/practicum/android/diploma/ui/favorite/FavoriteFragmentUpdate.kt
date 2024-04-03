package ru.practicum.android.diploma.ui.favorite

import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

sealed class FavoriteFragmentUpdate {

    data object EmptyVacancyList : FavoriteFragmentUpdate()

    data object GetVacanciesError : FavoriteFragmentUpdate()

    data class VacancyList(
        val vacancies: List<Vacancy>
    ) : FavoriteFragmentUpdate()
}
