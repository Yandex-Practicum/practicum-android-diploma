package ru.practicum.android.diploma.filter.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class RegionArea(
    @SerializedName("id") val id: String? = null,
    @SerializedName("parent_id") val parentId: String? = null,
    @SerializedName("name") val name: String? = "",
    @SerializedName("areas") val areas: List<RegionArea?>? = null
): Parcelable