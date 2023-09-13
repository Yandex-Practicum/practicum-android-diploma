package ru.practicum.android.diploma.details.data.network.dto.assistants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExperienceDto(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String?
)
