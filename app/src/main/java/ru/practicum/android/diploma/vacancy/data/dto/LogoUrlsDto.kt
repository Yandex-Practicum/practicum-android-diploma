package ru.practicum.android.diploma.vacancy.data.dto

import com.google.gson.annotations.SerializedName

data class LogoUrlsDto(
    @SerializedName("90") val iconSmall: String? = null,
    @SerializedName("240") val iconBig: String? = null,
    val original: String? = null,
)
