package ru.practicum.android.diploma.ui.filter.workplace.country

import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.ui.filter.workplace.region.RegionState

sealed interface CountryState {
    data object Loading : CountryState

    data class Empty(
        val message: Int
    ) : CountryState

    data class Content(
        val region: List<Country>
    ) : CountryState

    data class Error(
        val errorMessage: Int
    ) : CountryState
}
