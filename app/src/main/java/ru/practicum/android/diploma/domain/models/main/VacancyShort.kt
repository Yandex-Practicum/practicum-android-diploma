package ru.practicum.android.diploma.domain.models.main

import ru.practicum.android.diploma.data.dto.main.SalaryDto
import ru.practicum.android.diploma.domain.models.additional.Schedule


data class VacancyShort(
    val postedAt: String,
    val vacancyId: String,
    val logoUrl: LogoUrls?,
    val name: String,
    val area: String,
    val employer: String,
    val salary: SalaryDto,
    val schedule: Schedule? = null,
    val industries: List<Industry> = emptyList()
)
