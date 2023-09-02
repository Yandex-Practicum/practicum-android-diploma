package ru.practicum.android.diploma.features.filters.domain.models

data class Filter(
    val industry: Industry?,
    val country: Country?,
    val region: Region?,
    val salary: Int?,
    val doNotShowWithoutSalary: Boolean?
)
