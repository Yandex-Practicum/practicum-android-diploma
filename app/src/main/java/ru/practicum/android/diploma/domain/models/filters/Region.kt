package ru.practicum.android.diploma.domain.models.filters

data class Region(
    val id: String,
    val name: String,
    val regionId: String?,
    val countryId: String?,
    val countryName: String?
)
