package ru.practicum.android.diploma.core.domain.models

data class Filters(
    val area: String? = null,
    val industry: String? = null,
    val salary: String? = null,
    val onlyWithSalary: Boolean = false
)
