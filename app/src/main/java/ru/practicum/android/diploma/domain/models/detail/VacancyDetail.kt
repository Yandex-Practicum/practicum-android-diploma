package ru.practicum.android.diploma.domain.models.detail

data class VacancyDetail(
    val id: String,
    val name: String,
    val area: String,
    val vacancyLink: String,
    val contactName: String?,
    val contactEmail: String?,
    val contactPhone: String?,
    val contactComment: String?,
    val employerName: String?,
    val employerUrl: String?,
    val salary: String?,
    val schedule: String?,
    val employment: String?,
    val experience: String?,
    val keySkills: String,
    val description: String,
    val isFavorite: Boolean = false
)
