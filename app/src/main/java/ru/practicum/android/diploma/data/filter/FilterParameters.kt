package ru.practicum.android.diploma.data.filter

data class FilterParameters(
    val countryId: String? = null,
    val countryName: String? = null,
    val regionId: String? = null,
    val regionName: String? = null,
    val industryId: String? = null,
    val industryName: String? = null,
    val expectedSalary: Int? = null,
    val isWithoutSalaryShowed: Boolean = false
)
