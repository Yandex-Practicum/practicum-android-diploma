package ru.practicum.android.diploma.filter.data.model

sealed interface Filter {
    object CountryRequest: Filter
    data class RegionRequest(val query: String): Filter

}