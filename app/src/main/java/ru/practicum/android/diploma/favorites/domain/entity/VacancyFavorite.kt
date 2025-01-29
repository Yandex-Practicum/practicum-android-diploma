package ru.practicum.android.diploma.favorites.domain.entity

import ru.practicum.android.diploma.search.domain.model.Salary

data class VacancyFavorite(
    val id: String,
    val name: String,
    val salary: Salary?,
    val companyLogo: String?,
    val companyName: String,
    val area: String,
    val address: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val description: String,
    val keySkills: String,
    val vacancyUrl: String
)
