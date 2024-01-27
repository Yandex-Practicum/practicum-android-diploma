package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vacancy(
    val argument: String,
    val disable_url: String,
    val cluster_group: ArrayList<String>,
    val value: String,
    val value_description: String

) : Parcelable
