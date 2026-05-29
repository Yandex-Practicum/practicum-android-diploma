package ru.practicum.android.diploma.domain.models

sealed interface VacancyAction{
    data class EmailClick(val email: String, val title : String) : VacancyAction
    data class PhoneNumberClick(val phoneNumber: String, val title : String) : VacancyAction
    data class ShareVacancy(val vacancyLink: String, val title : String) : VacancyAction
}
