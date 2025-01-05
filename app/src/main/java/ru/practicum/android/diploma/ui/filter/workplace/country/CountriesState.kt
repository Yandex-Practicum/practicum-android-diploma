package ru.practicum.android.diploma.ui.filter.workplace.country

import ru.practicum.android.diploma.domain.models.Country

sealed interface CountriesState {

    object Loading : CountriesState

    data class Content(
        val data: List<Country>
    ) : CountriesState

    data class Error(
        val message: String
    ) : CountriesState

    object NotFound : CountriesState
}
