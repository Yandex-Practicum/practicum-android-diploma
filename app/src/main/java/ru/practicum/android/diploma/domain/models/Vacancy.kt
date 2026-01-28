package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vacancy(
    val id: String,
    val name: String,
    val description: String,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val areaName: String,
    val industryName: String?,
    val skills: List<String>?,
    val url: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val salaryTitle: String?,
    val city: String?,
    val street: String?,
    val building: String?,
    val fullAddress: String?,
    val contactName: String?,
    val email: String?,
    val phones: List<String>?,
    val employerName: String,
    val logoUrl: String?
) : Parcelable
