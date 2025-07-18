package ru.practicum.android.diploma.data.dto.vacancy.vacancyelements

import kotlinx.serialization.Serializable

@Serializable
data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String
)
