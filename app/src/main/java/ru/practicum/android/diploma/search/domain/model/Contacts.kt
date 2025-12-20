package ru.practicum.android.diploma.search.domain.model


data class Contacts(
    val id: String,
    val name: String,
    val email: String,
    val phone: List<String>
)
