package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Gradient(
    val angle: Int,
    @SerializedName("color_list") val colorList: List<Color>,
) : Parcelable
