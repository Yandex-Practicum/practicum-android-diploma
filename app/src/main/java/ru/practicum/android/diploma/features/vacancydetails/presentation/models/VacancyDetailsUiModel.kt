package ru.practicum.android.diploma.features.vacancydetails.presentation.models

data class VacancyDetailsUiModel(
    val vacancyId: String,
    val vacancyName: String,
    val salary: String,
    val logoUrl: String,
    val employerName: String,
    val employerArea: String,
    val experience: String,
    val employmentTypes: String,
    val vacancyDescription: String,
    val keySkills: String,
    val contactsName: String,
    val contactsEmail: String,
    val contactsPhones: List<String>,
    val responseUrl: String
)