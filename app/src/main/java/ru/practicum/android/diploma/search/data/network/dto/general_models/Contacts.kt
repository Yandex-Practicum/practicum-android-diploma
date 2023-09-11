package ru.practicum.android.diploma.search.data.network.dto.general_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contacts(
    @SerialName("email") val email: String? = "",
    @SerialName("name") val name: String? = "",
    @SerialName("phones") val phones: List<Phone>? = emptyList(),
)