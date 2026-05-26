package ru.practicum.android.diploma.presentation.vacancy.model

data class VacancyDetailContentUi(
    val id: String,
    val title: String,
    val salaryText: String,
    val companyName: String,
    val logoUrl: String?,
    val locationText: String?,
    val experience: String?,
    val employmentAndSchedule: String?,
    val descriptionHtml: String,
    val skills: List<String>,
    val contactName: String?,
    val email: String?,
    val phones: List<VacancyPhoneUi>,
    val shareUrl: String,
    val isFavorite: Boolean,
)

data class VacancyPhoneUi(
    val formatted: String,
    val comment: String?,
)
