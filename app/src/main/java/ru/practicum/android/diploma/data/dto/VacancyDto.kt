package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.components.Area
import ru.practicum.android.diploma.data.components.Employer
import ru.practicum.android.diploma.data.components.Salary

data class VacancyDto(
    val id: Int,
    val name: String,
    val employer: Employer,
    val salary: Salary?,
    val area: Area
)
