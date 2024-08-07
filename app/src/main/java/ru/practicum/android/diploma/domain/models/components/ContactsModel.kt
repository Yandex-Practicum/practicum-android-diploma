package ru.practicum.android.diploma.domain.models.components

data class ContactsModel(
    val email: String?,
    val name: String?,
    val phones: List<PhonesModel>?
)
