package ru.practicum.android.diploma.core.data.dto

import ru.practicum.android.diploma.core.data.dto.SalaryDto

data class VacancyDto(
    val id: String,
    val name: String,
    val company: String?,
    val city: String?,
    val salary: SalaryDto?,
    val logo: String?
)
