package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.CodeResponse


@Serializable
data class RegionListDto (
    @SerialName("areas") val areas: List<RegionDto?>? = null
) : CodeResponse() {

    companion object {
        val empty = RegionListDto(areas = null)
    }
}