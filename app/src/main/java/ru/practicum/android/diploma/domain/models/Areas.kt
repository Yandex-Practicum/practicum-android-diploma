package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.practicum.android.diploma.domain.models.`object`.Area

@Parcelize
data class Areas(
    val area: Area,
    val id: String,
    val name: String,
    val parent_id: String?
) : Parcelable
