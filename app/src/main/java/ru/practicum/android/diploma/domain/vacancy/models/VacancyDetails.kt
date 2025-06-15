package ru.practicum.android.diploma.domain.vacancy.models

data class VacancyDetails(
    val id: String,
    val title: String,
    val employerName: String,
    val logoUrl: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val descriptionHtml: String?,
    val keySkills: List<String>?
)

