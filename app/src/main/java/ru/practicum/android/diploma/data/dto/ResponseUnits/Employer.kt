package ru.practicum.android.diploma.data.dto.ResponseUnits

data class Employer(
    val id: String,
    val logo_urls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
)
