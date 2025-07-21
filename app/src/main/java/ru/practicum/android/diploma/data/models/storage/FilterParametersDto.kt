package ru.practicum.android.diploma.data.models.storage

data class FilterParametersDto(
    val countryName: String? = null,
    val countryId: String? = null,
    val regionName: String? = null,
    val industryId: String? = null,
    val industryName: String? = null,
    val salary: String? = null,
    val checkboxWithoutSalary: Boolean? = null,
)
