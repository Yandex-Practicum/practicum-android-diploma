package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val vacancy: Vacancy,
    val employment: String?,
    val keySkills: List<String>,
    val employmentForm: String,
    val experience: String,
    val professionalRoles: List<String>,
    val description: String,
    val schedule: List<String>,
    val address: String,
    val isFavorite: Boolean,
    val url: String?
)
