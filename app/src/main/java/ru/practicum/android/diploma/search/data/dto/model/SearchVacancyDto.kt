package ru.practicum.android.diploma.search.data.dto.model

data class SearchVacancyDto(
    val id: String,
    val name: String,
    val salary: SalaryDto? = null,
    val employer: EmployerDto? = null,
    val area: AreaDto? = null,
)
