package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.Serializable

@Serializable
data class PhoneDto(
    val number: String?,
    val city: String?,
    val country: String?
)
