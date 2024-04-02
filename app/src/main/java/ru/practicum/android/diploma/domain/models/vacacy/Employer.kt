package ru.practicum.android.diploma.domain.models.vacacy

data class Employer(
    val id: String,
    val logoUrls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
    val vacanciesUrl: String?
)
