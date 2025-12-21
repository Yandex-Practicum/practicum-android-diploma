package ru.practicum.android.diploma.search.data.dto

data class ContactsDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: List<String>
)
