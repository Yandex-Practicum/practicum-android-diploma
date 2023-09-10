package ru.practicum.android.diploma.search.data.network.dto.general_models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Phone(
    @SerialName("city") val city: String? = "",
    @SerialName("comment") val comment: String? = "",
    @SerialName("country") val country: String? = "",
    @SerialName("number") val number: String? = "",
)