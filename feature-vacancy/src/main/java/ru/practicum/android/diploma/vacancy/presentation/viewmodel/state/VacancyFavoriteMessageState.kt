package ru.practicum.android.diploma.vacancy.presentation.viewmodel.state

internal sealed interface VacancyFavoriteMessageState {
    data object NoAddFavorite : VacancyFavoriteMessageState
    data object NoDeleteFavorite : VacancyFavoriteMessageState
    data object Error : VacancyFavoriteMessageState
    data object Empty : VacancyFavoriteMessageState
}
