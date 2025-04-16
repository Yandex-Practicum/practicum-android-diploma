package ru.practicum.android.diploma.domain.models

data class Employer(
    val id: String,
    val logoUrls: LogoUrls? = null,
    val name: String
)
