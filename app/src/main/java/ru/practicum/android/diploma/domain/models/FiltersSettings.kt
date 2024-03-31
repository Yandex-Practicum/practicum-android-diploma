package ru.practicum.android.diploma.domain.models

data class FiltersSettings(
    val country: String,
    val countryId: String,
    val region: String,
    val regionId: String,
    val industry: String,
    val industryId: String,
    val expectedSalary: String,
    val salaryOnlyCheckbox: Boolean
)
