package ru.practicum.android.diploma.data.filter

data class FilterParameters(
    val countryId: Int? = null,
    val countryName: String? = null,
    val regionId: Int? = null,
    val regionName: String? = null,
    val industryId: Int? = null,
    val industryName: String? = null,
    val expectedSalary: Long? = null,
    val isWithoutSalaryShowed: Boolean = false
    )
