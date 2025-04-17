package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.Serializable

@Serializable
data class SalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: String?,
    val gross: Boolean?
)
