package ru.practicum.android.diploma.vacancy.domain.entity

data class Vacancy(
    val id: String,
    val name: String,
    val salary: String?,
    val companyLogo: String?,
    val companyName: String?,
    val area: String?,
    val address: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val description: String?,
    val keySkills: String?,
    val vacancyUrl: String?
)
