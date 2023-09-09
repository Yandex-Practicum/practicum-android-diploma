package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    val id: String?,
    val name: String?,
    val areas: List<RegionArea?>
)