package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val name: String,
    val salaryFrom: Int,
    val salaryTo: Int,
    val area: String,
    val employer: String,
    val employerLogo: String,
    val experience: String,
    val schedule: String,
    val professionalRoles: List<ProfessionalRole>,
    val snippetTitle: String,
    val snippetDescription: String,
    val employment: String,
)
