package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val area: String?,
    val employerName: String?,
    val name: String,
    val salary: String,
    val logoUrl90: String?
)
