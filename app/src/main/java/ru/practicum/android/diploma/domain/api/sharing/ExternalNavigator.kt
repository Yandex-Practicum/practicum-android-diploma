package ru.practicum.android.diploma.domain.api.sharing

interface ExternalNavigator {
    fun shareApp(url: String)
    fun makeCall(phone: String)
    fun openEmail(email: String)
}
