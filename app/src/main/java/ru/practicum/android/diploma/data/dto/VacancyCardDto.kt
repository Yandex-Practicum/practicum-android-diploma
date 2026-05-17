package ru.practicum.android.diploma.data.dto

data class VacancyCardDto(
    val id: String,
    val name: String,
    val company: String?,
    val city: String?,
    val salary: VacancyCardSalaryDto?,
    val logo: String?,
)
