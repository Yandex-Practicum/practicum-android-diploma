package ru.practicum.android.diploma.domain.models.components

data class EmployerModel(
    val id: String?,
    val logoUrls: LogoUrlModel?,
    val name: String?,
    val trusted: Boolean?,
    val url: String?,
    val vacanciesUrl: String?
)
