package ru.practicum.android.diploma.search.data.network.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class RegionArea(
    val id: String? = "",
    val parent_id: String? = "",
    val name: String? = "",
): Parcelable