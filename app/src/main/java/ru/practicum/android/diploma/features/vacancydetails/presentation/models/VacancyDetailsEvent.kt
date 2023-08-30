package ru.practicum.android.diploma.features.vacancydetails.presentation.models

sealed class VacancyDetailsEvent {

    data class WriteEmail(val email: String) : VacancyDetailsEvent()

    data class DialNumber(val phoneNumber: String) : VacancyDetailsEvent()

    data class ShareVacancy(val url: String) : VacancyDetailsEvent()

}