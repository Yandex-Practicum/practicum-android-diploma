package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val title: String,
    val description: String,
    val salary: Salary?,
    val employer: Employer?,
    val area: Area?
)
