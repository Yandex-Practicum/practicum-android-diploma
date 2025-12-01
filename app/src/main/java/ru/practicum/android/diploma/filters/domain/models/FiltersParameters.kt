package ru.practicum.android.diploma.filters.domain.models

data class FiltersParameters(
    val salary: Int?,
    val onlyWithSalary: Boolean = false,
    val industry: Int? = null,
    val area: String?
)
