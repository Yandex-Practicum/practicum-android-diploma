package ru.practicum.android.diploma.search.domain.models.assistants

data class Contacts(
    val email: String?,
    val name: String?,
    val phones: List<Phone>?
)