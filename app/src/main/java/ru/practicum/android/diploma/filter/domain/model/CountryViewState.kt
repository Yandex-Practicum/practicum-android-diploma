package ru.practicum.android.diploma.filter.domain.model

interface CountryViewState {
    data class Success(val countryList: List<Country>) : CountryViewState
    data object NotFoundError : CountryViewState
    data object ServerError : CountryViewState
    data object ConnectionError : CountryViewState
    data class CountrySelected(val country: Country) : CountryViewState
}
