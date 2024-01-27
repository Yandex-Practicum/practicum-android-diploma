package ru.practicum.android.diploma.domain.models.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClusterGroup(
    val id: String,
    val name: String
): Parcelable
