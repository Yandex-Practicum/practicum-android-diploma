package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.RegionArea

@Serializable
data class CountryDto(
    val id: String?,
    val name: String?,
    val areas: List<RegionArea?>
)