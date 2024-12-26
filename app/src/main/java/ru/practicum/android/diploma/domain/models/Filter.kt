package ru.practicum.android.diploma.domain.models

data class Filter(
    var country: Country? = null,
    var region: Region? = null,
    var industry: Industry? = null,
    var salary: Int? = null,
    var onlyWithSalary: Boolean = false
)
