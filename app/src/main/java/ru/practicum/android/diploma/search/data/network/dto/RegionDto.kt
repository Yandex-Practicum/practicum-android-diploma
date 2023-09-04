package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class RegionDto(
    val name: String?,
    val area: RegionArea?,
)