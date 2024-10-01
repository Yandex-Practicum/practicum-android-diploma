package ru.practicum.android.diploma.vacancy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vacancy(
    val idVacancy: Int,
    val nameVacancy: String,
    val salary: String,
    val nameCompany: String,
    val location: String,
    val urlLogo: String?,
    val dateAddVacancy: Long = -1L,
) : Parcelable
