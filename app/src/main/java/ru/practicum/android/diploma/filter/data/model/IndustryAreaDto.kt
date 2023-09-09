package ru.practicum.android.diploma.filter.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class IndustryAreaDto(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
)