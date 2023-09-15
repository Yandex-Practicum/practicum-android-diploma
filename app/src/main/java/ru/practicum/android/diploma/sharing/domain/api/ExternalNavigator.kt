package ru.practicum.android.diploma.sharing.domain.api

interface ExternalNavigator {
    fun sendVacancy(link: String?)
    fun writeEmail(link: String?)
    fun makeCall(link: String?)
}