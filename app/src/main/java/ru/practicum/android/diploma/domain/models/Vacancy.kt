package ru.practicum.android.diploma.domain.models


import ru.practicum.android.diploma.domain.models.`object`.Area
import ru.practicum.android.diploma.domain.models.`object`.Salary
data class Vacancy (
    val id: String,
    val area: Area?,
    val name: String,
    val salary: Salary?,
)
