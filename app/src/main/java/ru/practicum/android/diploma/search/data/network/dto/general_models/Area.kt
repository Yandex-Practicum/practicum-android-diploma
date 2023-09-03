package ru.practicum.android.diploma.search.data.network.dto.general_models

import kotlinx.serialization.Serializable

@Serializable
data class Area(
    val id: String? = "",
    val name: String? = "",
    val url: String? = "",
)