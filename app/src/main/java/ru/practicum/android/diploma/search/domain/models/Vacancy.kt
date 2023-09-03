package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String,
    val found: Int,
    val employerLogoUrl: String?,
    val salaryCurrency: String?,
    val salaryFrom: String?,
    val salaryTo: String?,
)
