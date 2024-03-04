package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val id: String,
    val parentId: String,
    val name: String
) : Parcelable
