package ru.practicum.android.diploma.data.dto.additional

import kotlinx.serialization.Serializable

@Serializable
data class ContactsDto(
    val name: String?,
    val email: String?,
    val phones: List<PhoneDto>?
)
