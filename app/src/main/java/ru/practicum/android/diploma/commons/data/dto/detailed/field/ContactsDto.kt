package ru.practicum.android.diploma.commons.data.dto.detailed.field

data class ContactsDto(
    val email: String?,
    val name: String?,
    val phones: List<PhonesDto>?
)
