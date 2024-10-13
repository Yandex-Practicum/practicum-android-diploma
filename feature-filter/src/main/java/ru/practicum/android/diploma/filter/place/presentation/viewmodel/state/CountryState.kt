package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

import ru.practicum.android.diploma.filter.place.domain.model.Country

sealed interface CountryState {
    data class Content(val countries: List<Country>) : CountryState
    object Empty : CountryState
    object Error : CountryState
}
