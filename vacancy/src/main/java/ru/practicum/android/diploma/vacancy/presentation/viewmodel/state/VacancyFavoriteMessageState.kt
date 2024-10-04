package ru.practicum.android.diploma.vacancy.presentation.viewmodel.state

sealed interface VacancyFavoriteMessageState {
    object NoAddFavorite : VacancyFavoriteMessageState
    object NoDeleteFavorite : VacancyFavoriteMessageState
    object Error : VacancyFavoriteMessageState
    object Empty : VacancyFavoriteMessageState
}
