package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.Serializable

@Serializable
data class PhoneDto(
    val number: String? = null,
    val city: String? = null,
    val country: String? = null
)
