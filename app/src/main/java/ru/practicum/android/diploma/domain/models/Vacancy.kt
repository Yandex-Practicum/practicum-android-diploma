package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val vacancyId: String,
    val vacancyName: String,
    val employerId: String?,
    val companyName: String?,
    val areaId: String?,
    val areaName: String?,
    val salaryFrom: Long?,
    val salaryTo: Long?,
    val currency: String?,
    val logoUrl: String?,
    val description: String?,
    val experienceName: String?,
    val scheduleName: String?,
    val employmentName: String?,
    val addressRaw: String?,
    val skills: List<String>?,
    val contactName: String?,
    val contactEmail: String?,
    val phoneFormatted: String?,
    val shareUrl: String?
)
