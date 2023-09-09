package ru.practicum.android.diploma.filter.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class IndustryAreaDto(
    val id: String?,
    val name: String?,
) : Parcelable