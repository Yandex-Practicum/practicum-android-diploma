package ru.practicum.android.diploma.ui.country

import ru.practicum.android.diploma.domain.country.Country

sealed interface CountryState {
    data object Loading : CountryState

    data class Content(
        val region: List<Country>
    ) : CountryState

    data class Error(
        val errorMessage: Int
    ) : CountryState
}
