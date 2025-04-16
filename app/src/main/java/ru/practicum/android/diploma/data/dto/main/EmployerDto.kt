package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployerDto(
    val id: String?,
    val name: String?,
    @SerialName("logo_urls") val logoUrls: LogoUrlsDto?,
    val url: String?,
    val trusted: Boolean = false
)
