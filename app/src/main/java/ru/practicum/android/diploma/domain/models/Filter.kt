package ru.practicum.android.diploma.domain.models

data class Filter(
    val country: String?,
    val region: String?,
    val industry: String?,
    val salary: String?,
    val onlyWithSalary: Boolean?
)
