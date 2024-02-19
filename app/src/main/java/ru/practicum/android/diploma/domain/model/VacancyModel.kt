package ru.practicum.android.diploma.domain.model

data class VacancyModel(
    val id: String,
    val vacancyName: String,
    val city: String,
    val salary: String,
    val companyName: String,
    val logoUrls: ArrayList<String>,
    val details: DetailVacancy?
)
