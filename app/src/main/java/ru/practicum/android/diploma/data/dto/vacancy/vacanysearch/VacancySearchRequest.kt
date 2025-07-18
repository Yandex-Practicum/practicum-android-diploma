package ru.practicum.android.diploma.data.dto.vacancy.vacanysearch

data class VacancySearchRequest(
    val page: Int,
    val perPage: String = "20",
    val text: String,
    val area: String?,
    val industry: String?,
    val salary: Long?,
    val onlyWithSalary: Boolean?
)
