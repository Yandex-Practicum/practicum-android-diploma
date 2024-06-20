package ru.practicum.android.diploma.domain.favorites

import ru.practicum.android.diploma.domain.search.models.DomainVacancy

sealed class VacancyViewState {
    object VacancyIsFavorite : VacancyViewState()
    object VacancyIsNotFavorite : VacancyViewState()
    data class VacancyDataDetail(val domainVacancy: DomainVacancy) : VacancyViewState()
    object VacancyLoading : VacancyViewState()
}
