package ru.practicum.android.diploma.core.domain.model

data class ShortVacancy(
    val id: Long,
    val name: String,
    val city: String,
    val salaryFrom: String,
    val salaryTo: String,
    val currency: String,
    val employerLogoUrl: String,
)
