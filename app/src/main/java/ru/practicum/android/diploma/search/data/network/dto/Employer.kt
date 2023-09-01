package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Employer(
    val accredited_it_employer: Boolean? = false,
    val alternate_url: String? = "",
    val id: String? = "",
    val logo_urls: LogoUrls? = LogoUrls(),
    val name: String? = "",
    val trusted: Boolean? = false,
    val url: String? = "",
)