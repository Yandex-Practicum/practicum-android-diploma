package ru.practicum.android.diploma.search.domain.model

data class VacancyFilter(
    val area: Int? = null,
    val industry: Int? = null,
    val text: String? = null,
    val salary: Int? = null,
    val page: Int? = null,
    val onlyWithSalary: Boolean? = null
)
