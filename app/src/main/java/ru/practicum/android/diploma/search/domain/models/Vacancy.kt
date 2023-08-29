package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val city: String?,
    val employerName: String,
    val employerLogoUrl: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
)
