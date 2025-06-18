package ru.practicum.android.diploma.ui.vacancy

data class VacancyDetailsVO(
    val title: String,
    val salary: String?,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val description: String?,
    val addressOrRegion: String,
    val logoUrl: String?,
    val employerName: String,
    val keySkills: List<String>,
    val isFavorite: Boolean,
    val url: String?
)
