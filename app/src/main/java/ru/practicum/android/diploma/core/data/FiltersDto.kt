package ru.practicum.android.diploma.core.data

data class FiltersDto(
    val area: FilterDto? = null,
    val industry: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)

data class FilterDto(
    val id: String,
    val name: String
)
