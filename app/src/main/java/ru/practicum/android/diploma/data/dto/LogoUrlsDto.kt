package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class LogoUrlsDto(
    @SerializedName("90")
    val size90: String? = null,
    @SerializedName("240")
    val size240: String? = null,
    val original: String? = null
)
