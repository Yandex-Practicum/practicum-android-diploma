package ru.practicum.android.diploma.details.data.dto.assistants

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.general_models.Phone

@Serializable
data class ContactsDto(
    val email: String? = "",
    val name: String? = "",
    val phones: List<Phone?>? = emptyList(),
)