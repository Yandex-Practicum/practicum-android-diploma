package ru.practicum.android.diploma.search.presentation.models

data class VacancyUi(
    val id: String,
    val name: String,
    val employerName: String,
    val salary: String,
    val logoUrl: String? = null
)
