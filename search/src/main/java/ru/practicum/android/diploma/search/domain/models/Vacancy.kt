package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val title: String,
    val companyName: String,
    val salary: Int?,
    val salaryCurrency: String?,
    val companyLogo: String?,
)
