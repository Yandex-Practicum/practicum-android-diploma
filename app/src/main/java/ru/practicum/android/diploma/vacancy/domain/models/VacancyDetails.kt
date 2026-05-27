package ru.practicum.android.diploma.vacancy.domain.models

data class VacancyDetails(
    val id: String,
    val name: String,
    val salary: String,
    val employerName: String,
    val employerLogoUrl: String?,
    val city: String?,
    val address: String?,
    val areaName: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val description: String,
    val keySkills: List<String>,
    val contacts: Contacts?,
    val alternateUrl: String?
)
