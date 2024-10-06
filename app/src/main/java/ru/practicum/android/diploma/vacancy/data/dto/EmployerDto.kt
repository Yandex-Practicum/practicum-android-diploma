package ru.practicum.android.diploma.vacancy.data.dto

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?,
    val name: String,
)
