package ru.practicum.android.diploma.domain.models

sealed interface VacancyAction {
    data class EmailClick(val email: String) : VacancyAction
    data class PhoneNumberClick(val phoneNumber: String) : VacancyAction
    data class ShareVacancy(val vacancyLink: String) : VacancyAction
}
