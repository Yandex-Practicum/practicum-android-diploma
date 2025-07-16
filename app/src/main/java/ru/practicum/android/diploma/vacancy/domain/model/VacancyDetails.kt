package ru.practicum.android.diploma.vacancy.domain.model

data class VacancyDetails(
    val id: String,
    val name: String,
    val employerName: String?,
    val employerLogoUrl: String?,
    val area: String?,
    val salary: String,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val description: String,
    val keySkills: List<String>,
    val url: String?
)
