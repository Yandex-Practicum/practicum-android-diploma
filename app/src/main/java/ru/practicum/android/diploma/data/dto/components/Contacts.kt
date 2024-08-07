package ru.practicum.android.diploma.data.dto.components

data class Contacts(
    val email: String,
    val name: String,
    val phones: List<Phone>?,
)
