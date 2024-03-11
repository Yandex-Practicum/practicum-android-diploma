package ru.practicum.android.diploma.domain.detail

data class VacancyDetail(
    val id: String,
    val name: String,
    val area: String,
    val vacancyLink: String,
    val contactName: String?,
    val contactEmail: String?,
    val contactPhones: List<String?>,
    val contactComments: List<String?>,
    val employerName: String?,
    val employerUrl: String?,
    val salary: String?,
    val schedule: String?,
    val employment: String?,
    val experience: String?,
    val keySkills: List<String?>,
    val description: String,
    val address: String?,
    val isFavorite: Boolean = false
)
