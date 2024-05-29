package ru.practicum.android.diploma.domain.models

data class Filtration(
    val area: Area?,
    val industry: Industry?,
    val salary: String?,
    val onlyWithSalary: Boolean
)
