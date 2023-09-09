package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegionDto(
    @SerialName("name")
    val name: String? = null,
    @SerialName("areas")
    val areas: List<RegionArea?>? = null,
)