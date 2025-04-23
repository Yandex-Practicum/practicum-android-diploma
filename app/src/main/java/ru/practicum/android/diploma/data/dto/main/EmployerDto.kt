package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName

@Serializable
data class EmployerDto(
    val id: String? = null,
    val name: String? = null,
    @SerialName("logo_urls") val logoUrls: LogoUrlsDto? = null,
    val url: String? = null,
    val trusted: Boolean? = null
)
