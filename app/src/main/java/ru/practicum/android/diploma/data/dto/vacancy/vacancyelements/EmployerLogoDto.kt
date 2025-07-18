package ru.practicum.android.diploma.data.dto.vacancy.vacancyelements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployerLogoDto(
    @SerialName("240")
    val path: String,
)
