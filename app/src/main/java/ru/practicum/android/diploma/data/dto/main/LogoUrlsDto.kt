package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoUrlsDto(
    @SerialName("90") val logo90: String? = null,
    @SerialName("240") val logo240: String? = null,
    @SerialName("original") val original: String? = null
)
