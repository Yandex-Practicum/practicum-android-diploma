package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.fieldvacancydto.AreaDto
import ru.practicum.android.diploma.data.dto.fieldvacancydto.EmploerDto
import ru.practicum.android.diploma.data.dto.fieldvacancydto.SalaryDto

data class VacancyDto(
    val id: String,
    val area: AreaDto?,
    val alternateUrl: String,
    val employer: EmploerDto?,
    val name: String,
    val salary: SalaryDto?,
)
