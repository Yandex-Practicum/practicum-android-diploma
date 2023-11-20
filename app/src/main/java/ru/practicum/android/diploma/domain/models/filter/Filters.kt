package ru.practicum.android.diploma.domain.models.filter

data class Filters(
    val country: Country?,
    val area: Area?,
    val industries: List<Industry>?,
    val preferSalary: String?,
    val isIncludeSalary: Boolean
)
