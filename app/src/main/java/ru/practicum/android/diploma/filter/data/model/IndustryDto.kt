package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndustryDto(
    @SerialName("id") val id: String?,
    @SerialName("industries") val industries: List<IndustryAreaDto>?,
    @SerialName("name") val name: String,
)