package ru.practicum.android.diploma.data.dto.responseUnits

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("90")
    val art90: String?,
    @SerializedName("240")
    val art240: String?,
    val original: String
)
