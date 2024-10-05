package ru.practicum.android.diploma.vacancy.data.dto

data class LogoUrlsDto(
    @SerializedName("240") val size240: String,
    @SerializedName("90") val size90: String,
    val original: String
)
