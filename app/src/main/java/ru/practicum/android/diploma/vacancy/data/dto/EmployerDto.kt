package ru.practicum.android.diploma.vacancy.data.dto

data class EmployerDto(
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?,
    val name: String,
)
