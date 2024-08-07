package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.components.Area
import ru.practicum.android.diploma.data.dto.components.Employer
import ru.practicum.android.diploma.data.dto.components.Salary

data class VacancyDto(
    val id: String,
    val name: String?,
    val employer: Employer?,
    val salary: Salary?,
    val area: Area
)
