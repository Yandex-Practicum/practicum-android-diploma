package ru.practicum.android.diploma.domain.models

sealed class FavoritesVacancyViewState {
    object FavoritesVacancyError : FavoritesVacancyViewState()
    object FavoritesVacancyEmptyDataResult : FavoritesVacancyViewState()
    data class FavoritesVacancyDataResult(val listOfFavoriteVacancy: List<Vacancy>) : FavoritesVacancyViewState()
}
