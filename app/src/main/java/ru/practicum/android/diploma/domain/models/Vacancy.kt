package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val logoUrl90: String?,
    val area: String?,
    val salary: Salary?,
    val employer: String?,
    val description: String?
)
