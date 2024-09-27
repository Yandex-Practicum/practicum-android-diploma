package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HHRegionsResponseItem(
    val areas: List<AreaX>,
    val id: String,
    val name: String,
    @SerializedName("parent_id") val parentId: String?,
) : Parcelable
