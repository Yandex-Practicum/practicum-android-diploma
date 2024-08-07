package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.domain.models.components.AreaModel
import ru.practicum.android.diploma.domain.models.components.EmployerModel
import ru.practicum.android.diploma.domain.models.components.SalaryModel

data class Vacancy(
    val id: String,
    val area: AreaModel?,
    val employer: EmployerModel?,
    val name: String?,
    val salary: SalaryModel?
)
