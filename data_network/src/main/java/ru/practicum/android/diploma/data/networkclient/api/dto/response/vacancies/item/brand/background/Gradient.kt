package ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.brand.background

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gradient(
    val angle: Int,
    @SerializedName("color_list") val colorList: List<Color>,
) : Parcelable
