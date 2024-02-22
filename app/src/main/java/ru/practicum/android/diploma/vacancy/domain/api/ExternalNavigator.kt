package ru.practicum.android.diploma.vacancy.domain.api

interface ExternalNavigator {
    fun makeCall(phoneNumber: String)
    fun sendEmail(email: String)
    fun shareVacancy()
}
