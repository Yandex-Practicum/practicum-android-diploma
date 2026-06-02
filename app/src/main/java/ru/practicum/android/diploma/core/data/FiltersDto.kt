package ru.practicum.android.diploma.core.data

data class FiltersDto(
    val country: FilterDto? = null,
    val region: FilterDto? = null,
    val industry: FilterDto? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)

data class FilterDto(
    val id: String,
    val name: String
)
