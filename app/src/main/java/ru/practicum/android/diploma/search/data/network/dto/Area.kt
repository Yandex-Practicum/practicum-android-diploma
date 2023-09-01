package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Area(
    val id: String = "",
    val name: String = "",
    val url: String = "",
)