package ru.practicum.android.diploma.domain.models

data class VacancyCard(
    val vacancyId: String,
    val vacancyName: String,
    val companyName: String?,
    val areaName: String?,
    val salaryFrom: Long?,
    val salaryTo: Long?,
    val currency: String?,
    val logoUrl: String?
)
