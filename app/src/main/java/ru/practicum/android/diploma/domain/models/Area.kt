package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Area(
    val areas: List<Area>,
    val id: String,
    val name: String,
    val parentId: String?
) : Parcelable
