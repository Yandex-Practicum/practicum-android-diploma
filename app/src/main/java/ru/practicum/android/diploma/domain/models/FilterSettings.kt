package ru.practicum.android.diploma.domain.models

data class FilterSettings(
    val countryId: String? = null,
    val countryName: String? = null,
    val regionId: String? = null,
    val regionName: String? = null,
    val industryId: String? = null,
    val industryName: String? = null,
    val expectedSalary: Int? = null,
    val notShowWithoutSalary: Boolean = false
)
