package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndustryDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null
)
