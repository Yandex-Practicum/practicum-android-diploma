package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.Serializable


@Serializable
data class RegionDto(
    val name: String? = null,
    val areas: List<RegionArea?>? = null,
)