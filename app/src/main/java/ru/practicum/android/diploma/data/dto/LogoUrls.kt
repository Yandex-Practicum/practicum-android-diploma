package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("90")
    val url90: String,
    @SerializedName("240")
    val url240: String
)
