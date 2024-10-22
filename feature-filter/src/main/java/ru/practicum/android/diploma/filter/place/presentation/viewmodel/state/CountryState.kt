package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

import ru.practicum.android.diploma.filter.place.domain.model.Country

internal sealed interface CountryState {
    data object Loading : CountryState
    data class Content(val countries: List<Country>) : CountryState
    data object Empty : CountryState
    data object Error : CountryState
}
