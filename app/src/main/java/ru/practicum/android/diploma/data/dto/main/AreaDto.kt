package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.Serializable

@Serializable
data class AreaDto(
    val id: String? = null,
    val name: String? = null,
    val url: String? = null
)
