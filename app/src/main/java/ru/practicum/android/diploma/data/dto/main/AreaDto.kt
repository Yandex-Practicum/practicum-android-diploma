package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.Serializable

@Serializable
data class AreaDto(
    val id: String?,
    val name: String?,
    val url: String?
)
