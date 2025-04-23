package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName

@Serializable
data class IndustryDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null
)
