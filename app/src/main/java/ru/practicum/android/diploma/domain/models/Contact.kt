package ru.practicum.android.diploma.domain.models

data class Contact(
    val email: String?,
    val name: String?,
    val phones: Array<String>,
)