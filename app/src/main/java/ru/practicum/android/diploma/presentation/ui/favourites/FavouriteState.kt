package ru.practicum.android.diploma.presentation.ui.favourites

import androidx.annotation.StringRes
import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavouriteState {
    data class Content(
        val vacancy: List<Vacancy>
    ) : FavouriteState

    data class Empty(
        @StringRes val message: Int
    ) : FavouriteState
}
