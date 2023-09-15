package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionArea(
    @SerialName("id") val id: String? = null,
    @SerialName("parent_id") val parentId: String? = null,
    @SerialName("name") val name: String? = "",
    @SerialName("areas") val areas: List<RegionArea>? = null,
)