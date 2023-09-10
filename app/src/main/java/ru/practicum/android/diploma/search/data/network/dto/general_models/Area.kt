package ru.practicum.android.diploma.search.data.network.dto.general_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Area(
    @SerialName("id") val id: String? = "",
    @SerialName("name") val name: String? = "",
    @SerialName("url") val url: String? = "",
)