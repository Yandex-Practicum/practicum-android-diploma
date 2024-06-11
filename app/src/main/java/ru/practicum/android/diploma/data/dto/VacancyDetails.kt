package ru.practicum.android.diploma.data.dto

data class VacancyDetails(
    val id: String,
    val name: String,
    val employerName: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val area: Area,
    val publishedAt: String,
    val url: String,
    val description: String?,
    val keySkills: List<KeySkill>?
)

data class KeySkill(
    val name: String
)
