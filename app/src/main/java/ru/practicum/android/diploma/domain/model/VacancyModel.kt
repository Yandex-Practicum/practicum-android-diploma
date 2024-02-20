package ru.practicum.android.diploma.domain.model

data class VacancyModel(
    val id: String,
    val vacancyName: String,
    val city: String,
    val salary: String,
    val companyName: String,
    val logoUrls: ArrayList<String>,
    val details: Details?
)
data class Details(
    val experience: String,
    val employment: String,
    val description: String,
    val keySkills: ArrayList<String>,
    val managerName: String,
    val email: String,
    val phonesAndComments: ArrayList<Pair<String, String>>
)
