package ru.practicum.android.diploma.domain.models

data class FilterOptions(
    val page: String,
    val text: String,
    val area: String,
    val industry: String,
    val currency: String,
    val salary: String,
    val onlyWithSalary: String
)
