package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val id: String,
    val title: String,
    val area: Area,
    val companyName: String,
    val salaryMin: Int?,
    val salaryMax: Int?,
    val salaryCurrency: String?,
    val companyLogo: String?,
)
