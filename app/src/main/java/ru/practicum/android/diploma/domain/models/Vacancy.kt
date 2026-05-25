package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val company: String?,
    val city: String?,
    val salary: VacancySalary?,
    val logo: String?,
)
