package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("90")
    var size90: String? = null,
    @SerializedName("240")
    var size240: String? = null,
    var original: String? = null
)
