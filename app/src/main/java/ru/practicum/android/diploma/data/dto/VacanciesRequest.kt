package ru.practicum.android.diploma.data.dto

data class VacanciesRequest(
    val text: String = "",
    val page: Int = 1,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val area: String? = null,
    val industry: String? = null
)
