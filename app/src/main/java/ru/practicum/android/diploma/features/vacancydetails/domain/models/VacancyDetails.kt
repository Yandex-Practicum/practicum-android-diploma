package ru.practicum.android.diploma.features.vacancydetails.domain.models

data class VacancyDetails(
    val vacancyId: String,
    val vacancyName: String,
    val salary: Salary?,
    val logoUrl: String,
    val employerName: String,
    val employerArea: String,
    val experienceReq: String,
    val employmentType: String,
    val scheduleType: String,
    val vacancyDescription: String,
    val vacancyBrandedDesc: String,
    val keySkills: List<String>,
    val contactsName: String,
    val contactsEmail: String,
    val contactsPhones: List<String>,
    val responseUrl: String
)


