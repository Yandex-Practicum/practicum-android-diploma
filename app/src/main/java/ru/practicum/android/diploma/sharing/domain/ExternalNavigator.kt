package ru.practicum.android.diploma.sharing.domain

interface ExternalNavigator {

    fun shareLink(link: String)

    fun openEmail(email: String)

    fun openAppForCalls(number: String)
}
