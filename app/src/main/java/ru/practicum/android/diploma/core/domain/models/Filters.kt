package ru.practicum.android.diploma.core.domain.models

data class Filters(
    val country: Area? = null,
    val region: Area? = null,
    val industry: Industry? = null,
    val salary: String? = null,
    val onlyWithSalary: Boolean = false
)
