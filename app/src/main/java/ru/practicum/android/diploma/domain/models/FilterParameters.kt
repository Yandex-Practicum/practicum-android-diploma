package ru.practicum.android.diploma.domain.models

data class FilterParameters(
    val salary: Int? = null,
    val hideWithoutSalary: Boolean = false,
    val industryId: Int? = null,
    val industryName: String? = null,
    val area: Int? = null
)
