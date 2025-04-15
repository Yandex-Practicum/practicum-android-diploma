package ru.practicum.android.diploma.data.network.token

import ru.practicum.android.diploma.data.storage.AppPrefsService

class PrefsAccessTokenProvider(
    private val appPrefsService: AppPrefsService
) : AccessTokenProvider {
    override fun getAccessToken(): String {
        return checkNotNull(appPrefsService.getString(KEY)) {
            "Токен не найден"
        }
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
