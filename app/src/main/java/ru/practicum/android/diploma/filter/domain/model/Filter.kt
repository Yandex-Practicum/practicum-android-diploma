package ru.practicum.android.diploma.filter.domain.model

data class Filter(
    val country: Country? = null,
    val region: Region? = null,
    val industry: Industry? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
