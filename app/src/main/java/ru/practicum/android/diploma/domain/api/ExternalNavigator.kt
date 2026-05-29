package ru.practicum.android.diploma.domain.api

interface ExternalNavigator {
    fun openEmail(email: String, title: String)

    fun makePhoneCall(phoneNumber: String, title: String)

    fun shareLink(vacancyLink: String, title: String)
}
