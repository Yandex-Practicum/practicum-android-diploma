package ru.practicum.android.diploma.domain.models

data class CountryRegionFilter(
    val countryId: String?,
    val countryName: String?,
    val countryVisible: Boolean,
    val regionId: String?,
    val regionName: String?,
    val regionVisible: Boolean,
)
