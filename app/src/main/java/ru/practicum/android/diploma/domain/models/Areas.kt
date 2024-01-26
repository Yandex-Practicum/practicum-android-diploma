package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Areas(
    val areas: ArrayList<String>,
    val id: String,
    val name: String,
    val parent_id: String?
): Parcelable
