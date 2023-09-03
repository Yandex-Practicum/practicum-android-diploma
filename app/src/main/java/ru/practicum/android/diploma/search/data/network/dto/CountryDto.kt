package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.Response

@Serializable
data class CountryDto(
    val url: String? = "",
    val id: String? = "",
    var name: String? = "",
)