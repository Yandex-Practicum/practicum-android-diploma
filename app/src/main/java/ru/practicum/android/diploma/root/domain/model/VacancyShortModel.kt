package ru.practicum.android.diploma.root.domain.model

data class VacancyShortModel(
    val id: String,
    val employer: String,
    val artworkUrl: String?,
    val jobTitle: String,
    val region: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
)