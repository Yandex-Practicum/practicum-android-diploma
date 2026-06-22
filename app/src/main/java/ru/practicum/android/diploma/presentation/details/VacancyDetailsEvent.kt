package ru.practicum.android.diploma.presentation.details

sealed interface VacancyDetailsEvent {
    data class OpenPhone(val phone: String) : VacancyDetailsEvent
    data class OpenEmail(val email: String) : VacancyDetailsEvent
    data class Share(val text: String) : VacancyDetailsEvent
}
