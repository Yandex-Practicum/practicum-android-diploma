package ru.practicum.android.diploma.data.dto.field

data class ContactsDto(
    val email: String?,
    val name: String?,
    val phones: List<PhonesDto>?
)
