package ru.practicum.android.diploma.details.data.dto.assistants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.general_models.Phone

@Serializable
data class ContactsDto(
    @SerialName("email") val email: String? = "",
    @SerialName("name") val name: String? = "",
    @SerialName("phones") val phones: List<Phone?>? = emptyList(),
)