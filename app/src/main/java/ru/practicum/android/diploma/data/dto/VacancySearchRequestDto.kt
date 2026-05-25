package ru.practicum.android.diploma.data.dto

data class VacancySearchRequestDto(
    val text: String,
    val area: Int?,
    val industry: Int?,
    val salary: Int?,
    val page: Int,
    val onlyWithSalary: Boolean?,
)
