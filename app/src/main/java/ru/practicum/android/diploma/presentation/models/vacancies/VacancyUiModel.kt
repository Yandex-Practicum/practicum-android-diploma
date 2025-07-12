package ru.practicum.android.diploma.presentation.models.vacancies

data class VacancyUiModel(
    val nameVacancy: String,
    val alternateUrl: String,
    val id: String,
    val employerName: String?,
    val logo: String?,
    val salary: String,
    val city: String?,
    var isFavourite: Boolean = false,
    var timestamp: Long? = null
)
