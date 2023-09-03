package ru.practicum.android.diploma.filter.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val url: String? = "",
    val id: String? = "",
    var name: String? = "",
)