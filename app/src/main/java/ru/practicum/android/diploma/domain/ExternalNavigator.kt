package ru.practicum.android.diploma.domain

interface ExternalNavigator {
    fun sharePhone(phone: String)
    fun shareEmail(email: String)
    fun shareVacancyUrl(vacancyUrl: String)
}