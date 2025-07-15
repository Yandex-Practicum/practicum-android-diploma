package ru.practicum.android.diploma.domain.models.vacancydetails

import ru.practicum.android.diploma.domain.models.salary.Salary

data class VacancyDetails(
    val id: String,
    val name: String,
    val salary: Salary,
    val employer: String?,
    val experience: String?,
    val employmentForm: String?,
    val description: String,
    val workFormat: String?,
    val alternateUrl: String,
    val keySkills: List<String>?,
    val city: String?,
    val logoUrl: String?,
)
