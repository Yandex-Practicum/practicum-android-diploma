package ru.practicum.android.diploma.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Area(
    val id: String,
    val name: String
) : Parcelable {
    companion object
}

typealias Areas = List<Area>
