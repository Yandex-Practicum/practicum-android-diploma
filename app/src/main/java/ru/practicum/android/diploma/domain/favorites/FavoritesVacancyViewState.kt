package ru.practicum.android.diploma.domain.favorites

import ru.practicum.android.diploma.domain.search.models.DomainVacancy

sealed class FavoritesVacancyViewState {
    object FavoritesVacancyError : FavoritesVacancyViewState()
    object FavoritesVacancyEmptyDataResult : FavoritesVacancyViewState()
    data class FavoritesVacancyDataResult(val listOfFavoriteDomainVacancy: List<DomainVacancy>) : FavoritesVacancyViewState()
}
