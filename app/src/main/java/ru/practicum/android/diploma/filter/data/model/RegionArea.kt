package ru.practicum.android.diploma.filter.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class RegionArea(
    val id: String? = "",
    @SerializedName("parent_id") val parentId: String? = "",
    val name: String? = "",
    val areas: List<RegionArea?>? = null
): Parcelable