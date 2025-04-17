package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoUrlsDto(
    @SerialName("90") val logo90: String?,
    @SerialName("240") val logo240: String?,
    @SerialName("original") val original: String?
)
