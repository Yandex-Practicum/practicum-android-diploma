package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val city: String? = null,
    val street: String? = null,
    val building: String? = null
)
