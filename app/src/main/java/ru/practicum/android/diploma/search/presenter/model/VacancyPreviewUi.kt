package ru.practicum.android.diploma.search.presenter.model

data class VacancyPreviewUi(
    val id: Int,
    val found: Int,
    val name: String,
    val employerName: String,
    val salary: String,
    val logoUrl: String?
)
