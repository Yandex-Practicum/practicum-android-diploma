package ru.practicum.android.diploma.search.data.network.dto.general_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employer(
    @SerialName("accredited_it_employer") val accreditedItEmployer: Boolean? = false,
    @SerialName("alternate_url") val alternateUrl: String? = "",
    @SerialName("id") val id: String? = "",
    @SerialName("logo_urls") val logoUrls: LogoUrls? = LogoUrls(),
    @SerialName("name") val name: String? = "",
    @SerialName("trusted") val trusted: Boolean? = false,
    @SerialName("url") val url: String? = "",
)