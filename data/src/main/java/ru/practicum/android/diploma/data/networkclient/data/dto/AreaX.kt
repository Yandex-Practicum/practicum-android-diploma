package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AreaX(
    val areas: List<ru.practicum.android.diploma.data.networkclient.data.dto.Area>,
    val id: String,
    val name: String,
    @SerializedName("parent_id")
    val parentId: String,
) : Parcelable
