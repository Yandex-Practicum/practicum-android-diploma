package ru.practicum.android.diploma.filter.data.model

sealed interface Filter {
    object CountryRequest: Filter
    object CityRequest: Filter
}