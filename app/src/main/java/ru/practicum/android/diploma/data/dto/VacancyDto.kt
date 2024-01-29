package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.field.AreaDto
import ru.practicum.android.diploma.data.dto.field.EmploerDto
import ru.practicum.android.diploma.data.dto.field.SalaryDto

data class VacancyDto(
    val id: String,
    val area: AreaDto?,
    val alternateUrl: String,
    val employer: EmploerDto?,
    val name: String,
    val salary: SalaryDto?,
)
