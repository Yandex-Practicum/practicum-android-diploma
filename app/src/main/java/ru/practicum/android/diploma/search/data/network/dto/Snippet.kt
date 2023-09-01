package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Snippet(
    val requirement: String = "",
    val responsibility: String = "",
)