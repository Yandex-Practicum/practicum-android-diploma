package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.Serializable

@Serializable
data class ContactsDto(
    val name: String? = null,
    val email: String? = null,
    val phones: List<PhoneDto>? = null
)
