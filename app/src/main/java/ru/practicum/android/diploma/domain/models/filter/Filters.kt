package ru.practicum.android.diploma.domain.models.filter

data class Filters(
    val country: Country?,
    val area: Area?,
    val industry: Industry?,
    val preferSalary: String?,
    val isIncludeSalary: Boolean
)
