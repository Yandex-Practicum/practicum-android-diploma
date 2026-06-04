package ru.practicum.android.diploma.domain.models

data class FilterSettings(
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
    val countryId: Int? = null,
    val countryName: String? = null,
    val regionId: Int? = null,
    val regionName: String? = null,
    val industryId: Int? = null,
    val industryName: String? = null,
) {
    val hasActiveFilters: Boolean
        get() = salary.isNotBlank() ||
            onlyWithSalary ||
            countryId != null ||
            regionId != null ||
            industryId != null

    val salaryValue: Int?
        get() = salary.toIntOrNull()

    val areaId: Int?
        get() = regionId ?: countryId
}
