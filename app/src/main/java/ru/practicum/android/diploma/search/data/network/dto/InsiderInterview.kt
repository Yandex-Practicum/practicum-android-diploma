package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class InsiderInterview(
    val id: String = "",
    val url: String = "",
)