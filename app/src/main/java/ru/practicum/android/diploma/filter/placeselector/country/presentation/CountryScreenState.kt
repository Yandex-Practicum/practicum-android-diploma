package ru.practicum.android.diploma.filter.placeselector.country.presentation

import ru.practicum.android.diploma.core.domain.model.Country

sealed class CountryScreenState {
    class Content(val countries: ArrayList<Country>) : CountryScreenState()
    data object Error : CountryScreenState()
}
