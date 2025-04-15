package ru.practicum.android.diploma.data.network.token

import ru.practicum.android.diploma.data.shared_prefs.AppPrefsService

class PrefsAccessTokenProvider(
    private val appPrefsService: AppPrefsService
) : AccessTokenProvider {
    override fun getAccessToken(): String {
        return appPrefsService.getString(KEY)
            ?: throw IllegalStateException("Access token not found")
    }

    override fun saveAccessToken(token: String) {
        appPrefsService.putString(KEY, token)
    }

    override fun clearAccessToken() {
        appPrefsService.remove(KEY)
    }

    companion object {
        private const val KEY = "access_token"
    }
}
