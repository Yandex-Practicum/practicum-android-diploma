package ru.practicum.android.diploma.domain.models.filters

data class VacancyFilters (
    val text: String,
    val page: Int = 0,
    val perPage: Int = 20,
    val area: String? = null,
    val industry: String? = null,
    val currency: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
