package ru.practicum.android.diploma.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverPicture(
    @SerializedName("resized_height") val resizedHeight: Int,
    @SerializedName("resized_path") val resizedPath: String,
    @SerializedName("resized_width") val resizedWidth: Int,
) : Parcelable
