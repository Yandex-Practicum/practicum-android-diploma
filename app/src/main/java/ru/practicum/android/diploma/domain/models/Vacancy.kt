package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val employer: Employer? = null,
    val salary: Salary? = null,
    val salaryRange: Salary? = null
)
