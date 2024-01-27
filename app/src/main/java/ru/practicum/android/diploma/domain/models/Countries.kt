package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Countries(
    val id: String,
    val name: String,
    val url: String

) : Parcelable
