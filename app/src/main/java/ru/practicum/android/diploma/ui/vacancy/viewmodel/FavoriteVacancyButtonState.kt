package ru.practicum.android.diploma.ui.vacancy.viewmodel

sealed interface FavoriteVacancyButtonState {

    data object VacancyIsFavorite : FavoriteVacancyButtonState

    data object VacancyIsNotFavorite : FavoriteVacancyButtonState

}
