package ru.practicum.android.diploma.search.domain

data class Salary(
    val currency: String,
    val from: Int,
    val gross: Boolean,
    val to: Int?,
)
