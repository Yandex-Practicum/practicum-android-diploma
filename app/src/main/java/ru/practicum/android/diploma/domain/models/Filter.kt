package ru.practicum.android.diploma.domain.models

data class Filter(
    val country: String? = null,
    val region: String? = null,
    val industry: String? = null,
    val salary: String? = null,
    val onlyWithSalary: Boolean? = null
)
