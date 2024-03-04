package ru.practicum.android.diploma.data.dto.fields

data class ContactsDto(
    val email: String?,
    val name: String?,
    val phones: List<PhoneNumsDto>?,
)
