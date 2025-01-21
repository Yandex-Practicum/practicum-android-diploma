package ru.practicum.android.diploma.data.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val area: AreaDto?,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val description: String?
)
