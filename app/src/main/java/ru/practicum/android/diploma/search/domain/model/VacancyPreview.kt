package ru.practicum.android.diploma.search.domain.model

data class VacancyPreview(
    val id: Int,
    val found: Int,
    val name: String,
    val employerName: String,
    val from: Int?,
    val to: Int?,
    val currency: String?,
    val url: String?
)
