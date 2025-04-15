package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalaryDto(
    @SerialName("from") val from: Int? = null,
    @SerialName("to") val to: Int? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("gross") val gross: Boolean? = null
)
