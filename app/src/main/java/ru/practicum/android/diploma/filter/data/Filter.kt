package ru.practicum.android.diploma.filter.data

sealed interface Filter {
    object CountryRequest: Filter
    object CityRequest: Filter
}