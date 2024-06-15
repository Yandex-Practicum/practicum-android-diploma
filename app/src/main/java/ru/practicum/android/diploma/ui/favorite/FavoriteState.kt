package ru.practicum.android.diploma.ui.favorite

import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoriteState {
    object Empty : FavoriteState
    object Error : FavoriteState
    object Loading : FavoriteState
    data class Content(val favorite: List<Vacancy>, val currencyDictionary: Map<String, Currency>) : FavoriteState
}
