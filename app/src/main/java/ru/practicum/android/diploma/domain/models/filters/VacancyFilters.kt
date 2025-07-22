package ru.practicum.android.diploma.domain.models.filters

data class VacancyFilters (
    val text: String,
    val page: Int = 0,
    val perPage: Int = 20,
    val filter: FilterParameters
)
