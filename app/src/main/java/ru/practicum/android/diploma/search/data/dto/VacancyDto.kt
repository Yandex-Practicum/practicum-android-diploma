package ru.practicum.android.diploma.search.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class VacancyDto (
    val id: String,
    val name: String,
    val city: String?,
    val employerName: String,
    val employerLogoUrl: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
): Parcelable