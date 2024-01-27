package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Industry(
    val id: String,
    val industries: Array<String>,
    val name: String
) : Parcelable
