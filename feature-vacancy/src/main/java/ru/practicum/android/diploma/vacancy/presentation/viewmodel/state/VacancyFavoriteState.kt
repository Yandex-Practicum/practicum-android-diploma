package ru.practicum.android.diploma.vacancy.presentation.viewmodel.state

import ru.practicum.android.diploma.ui.R

internal sealed class VacancyFavoriteState(val stateViewButton: Int) {
    data object Favorite : VacancyFavoriteState(R.drawable.favorites_on)
    data object NotFavorite : VacancyFavoriteState(R.drawable.favorites_off)
}
