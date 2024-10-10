package ru.practicum.android.diploma.vacancy.presentation.viewmodel.state

import ru.practicum.android.diploma.ui.R

sealed class VacancyFavoriteState(val stateViewButton: Int) {
    object Favorite : VacancyFavoriteState(R.drawable.favorites_on)
    object NotFavorite : VacancyFavoriteState(R.drawable.favorites_off)
}
