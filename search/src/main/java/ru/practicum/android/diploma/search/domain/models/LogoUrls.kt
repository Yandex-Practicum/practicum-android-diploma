package ru.practicum.android.diploma.search.domain.models

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("240") val deg240: String?,
    @SerializedName("90") val deg90: String?,
    val original: String?,
)

