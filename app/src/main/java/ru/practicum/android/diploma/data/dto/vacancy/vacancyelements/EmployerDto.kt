package ru.practicum.android.diploma.data.dto.vacancy.vacancyelements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployerDto(
    val name: String,
    @SerialName("logo_urls")
    val employerLogo: EmployerLogoDto?
)
