package ru.practicum.android.diploma.search.data.network.dto.general_models

import kotlinx.serialization.Serializable

@Serializable
data class Contacts(
    val email: String? = "",
    val name: String? = "",
    val phones: List<Phone?>? = emptyList(),
)