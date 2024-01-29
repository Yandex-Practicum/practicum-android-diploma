package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vacancy(
    val id: String,
    val area: String?,
    val employerName: String?,
    val name: String,
    val salary: String,
    val logoUrl90: String?
) : Parcelable
