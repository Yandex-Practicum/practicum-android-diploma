package ru.practicum.android.diploma.ui.vacancy

data class VacancyDetailsVO(
    val title: String,
    val salary: String,
    val experience: String,
    val employment: String?,
    val schedule: List<String>,
    val description: String,
    val addressOrRegion: String,
    var isFavorite: Boolean
)
