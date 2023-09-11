package ru.practicum.android.diploma.details.data.dto.assistants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmploymentDto(
    @SerialName("id") val id: String?,
    @SerialName("name") val name: String?
)