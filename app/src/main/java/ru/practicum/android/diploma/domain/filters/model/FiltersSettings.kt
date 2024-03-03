package ru.practicum.android.diploma.domain.filters.model

data class FiltersSettings(
    val placeOfWork: String,
    val countryId: String,
    val regionId: String,
    val industry: String,
    val industryId: String,
    val expectedSalary: String,
    val salaryOnlyCheckbox: Boolean
)
