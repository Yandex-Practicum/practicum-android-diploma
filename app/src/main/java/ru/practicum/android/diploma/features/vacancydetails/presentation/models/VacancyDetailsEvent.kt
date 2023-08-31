package ru.practicum.android.diploma.features.vacancydetails.presentation.models

import ru.practicum.android.diploma.features.vacancydetails.domain.models.Email

sealed class VacancyDetailsEvent {

    data class ComposeEmail(val email: Email) : VacancyDetailsEvent()

    data class DialNumber(val phoneNumber: String) : VacancyDetailsEvent()

    data class ShareVacancy(val sharingText: String) : VacancyDetailsEvent()

}