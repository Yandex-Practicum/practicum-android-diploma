package ru.practicum.android.diploma.search.data.dto

data class VacancyDto (
    private val id: Int,
    private val name: String,
    private val employer: EmployerDto,
    private val salary: SalaryDto?,
    private val area: AreaDto
)
