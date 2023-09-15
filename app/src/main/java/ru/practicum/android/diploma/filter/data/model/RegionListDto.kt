package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionListDto (
    @SerialName("areas") val areas: List<RegionDto>? = null,
) {
    companion object {
        val empty = RegionListDto(areas = null)
    }
}