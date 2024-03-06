package ru.practicum.android.diploma.filter.domain.models

data class Filter(
    val country: String,
    val region: String,
    val industry: String,
    val salary: Int,
    val showWithSalary: Boolean
)
