package ru.practicum.android.diploma.domain.models

sealed class VacancyDetailsError {
    object Network : VacancyDetailsError()
    object NotFound : VacancyDetailsError()
    object Server : VacancyDetailsError()
}
