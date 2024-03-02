package ru.practicum.android.diploma.presentation.filters

import ru.practicum.android.diploma.domain.models.Country

sealed interface FiltersCountriesState {
    object Loading : FiltersCountriesState
    data class Content(val countries: List<Country>) : FiltersCountriesState
    object Error : FiltersCountriesState
    object Empty : FiltersCountriesState

}
