package ru.practicum.android.diploma.presentation.countryfiltersscreen.uistate

import ru.practicum.android.diploma.domain.models.filters.Country

sealed class CountryFiltersUiState {
    data object Loading : CountryFiltersUiState()
    data class Content(val countries: List<Country>) : CountryFiltersUiState()
    data object Error : CountryFiltersUiState()
}
