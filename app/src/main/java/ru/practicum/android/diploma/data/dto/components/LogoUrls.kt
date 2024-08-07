package ru.practicum.android.diploma.data.dto.components

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    val original: String?,
    @SerializedName("90")
    val logo90: String?,
    @SerializedName("240")
    val logo240: String?
)
