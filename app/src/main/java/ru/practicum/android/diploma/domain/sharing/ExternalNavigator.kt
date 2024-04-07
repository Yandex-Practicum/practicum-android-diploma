package ru.practicum.android.diploma.domain.sharing

interface ExternalNavigator {
    fun writeEmail(address: String)
    fun call(phone: String)
    fun share(url: String)
    fun openApplicationSettings()
}
