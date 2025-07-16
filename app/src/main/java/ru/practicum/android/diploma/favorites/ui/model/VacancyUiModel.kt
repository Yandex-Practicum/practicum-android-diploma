package ru.practicum.android.diploma.favorites.ui.model

data class VacancyUiModel(
    val id: String,
    val name: String,
    val employer: String?,
    val area: String?,
    val salary: String,
    val logoUrl: String?
)
