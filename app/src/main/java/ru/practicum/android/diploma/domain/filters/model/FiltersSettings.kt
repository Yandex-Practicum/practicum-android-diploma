package ru.practicum.android.diploma.domain.filters.model

data class FiltersSettings(
    val placeOfWork: String,
    val industry: String,
    val expectedSalary: String,
    val salaryOnlyCheckbox: Boolean
)
