package ru.practicum.android.diploma.favorites.domain.model

data class FavoriteVacancy(
    val idVacancy: Int,
    val nameVacancy: String,
    val salary: String,
    val nameCompany: String,
    val location: String,
    val urlLogo: String?,
    val dateAddVacancy: Long = -1L,
)
