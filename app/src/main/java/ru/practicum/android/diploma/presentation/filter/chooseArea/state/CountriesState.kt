package ru.practicum.android.diploma.presentation.filter.chooseArea.state

import ru.practicum.android.diploma.domain.models.filter.Country


sealed interface CountriesState {
    class Error(val errorText: String): CountriesState
    class DisplayCountries(val countries: ArrayList<Country>): CountriesState
}