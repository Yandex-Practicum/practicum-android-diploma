package ru.practicum.android.diploma.search.data.network.dto.general_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoUrls(
    @SerialName("240")
    val url240: String? = "",
    val original: String? = "",
)