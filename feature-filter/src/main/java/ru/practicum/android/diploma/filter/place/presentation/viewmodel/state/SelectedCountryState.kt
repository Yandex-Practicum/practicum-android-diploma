package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

import ru.practicum.android.diploma.filter.place.domain.model.Country

sealed interface SelectedCountryState {
    data class SelectedCountry(val country: Country) : SelectedCountryState
    object Empty : SelectedCountryState
    object Error : SelectedCountryState
}
