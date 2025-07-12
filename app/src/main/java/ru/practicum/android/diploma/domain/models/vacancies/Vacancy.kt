package ru.practicum.android.diploma.domain.models.vacancies

import ru.practicum.android.diploma.domain.models.salary.Salary

data class Vacancy(
    val nameVacancy: String,
    val alternateUrl: String,
    val id: String,
    val employerName: String?,
    val logo: String?,
    val salary: Salary,
    val city: String?,
    var timestamp: Long? = null
)
