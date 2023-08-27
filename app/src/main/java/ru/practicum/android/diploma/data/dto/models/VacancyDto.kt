package ru.practicum.android.diploma.data.dto.models

data class VacancyDto (
    val id: String,
    val name: String,
    val city: String?,
    val employerName: String,
    val employerLogoUrl: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
)