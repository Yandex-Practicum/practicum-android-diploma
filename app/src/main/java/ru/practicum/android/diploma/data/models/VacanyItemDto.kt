package ru.practicum.android.diploma.data.models

data class VacanyItemDto(
    val id: String,
    val name: String,
    val area: InfoClass,
    val employer: EmployerDto,
    val salary_range: SalaryRangeDto,
)


