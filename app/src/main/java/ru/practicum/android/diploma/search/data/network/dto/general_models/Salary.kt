package ru.practicum.android.diploma.search.data.network.dto.general_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Salary(
    @SerialName("currency") val currency: String? = "",
    @SerialName("from") val from: Int? = 0,
    @SerialName("gross") val gross: Boolean? = false,
    @SerialName("to") val to: Int? = 0,
)