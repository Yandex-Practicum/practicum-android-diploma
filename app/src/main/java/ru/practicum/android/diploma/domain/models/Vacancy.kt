package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val area: String,
    val employer: String?,
    val name: String,
    val salary: String?
)
