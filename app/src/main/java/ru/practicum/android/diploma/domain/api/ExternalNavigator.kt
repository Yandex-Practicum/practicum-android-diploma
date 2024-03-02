package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Email

interface ExternalNavigator {

    fun openUrlLink(link: String)

    fun sendEmail(emailData: Email)

    fun share(link: String)

    fun makePhoneCall(number: String)
}

