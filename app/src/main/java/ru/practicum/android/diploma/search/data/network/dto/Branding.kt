package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Branding(
    val tariff: String = "",
    val type: String = "",
)