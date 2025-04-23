package ru.practicum.android.diploma.data.dto.main

@Serializable
data class SalaryDto(
    val from: Int? = null,
    val to: Int? = null,
    val currency: String? = null,
    val gross: Boolean? = null
)
