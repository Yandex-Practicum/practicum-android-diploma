package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place

sealed interface PlaceState {
    data class ContentCountry(val country: Country) : PlaceState
    data class ContentPlace(val place: Place) : PlaceState
    data object Empty : PlaceState
    data object Error : PlaceState
}
