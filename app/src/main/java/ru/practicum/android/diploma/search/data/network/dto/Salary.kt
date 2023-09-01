package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Salary(
    val currency: String = "",
    val from: Int = 0,
    val gross: Boolean = false,
    val to: Int = 0,
)