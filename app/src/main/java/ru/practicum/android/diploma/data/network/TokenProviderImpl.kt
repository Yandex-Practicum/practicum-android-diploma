package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.BuildConfig

class TokenProviderImpl : TokenProvider {
    override fun getAccessToken(): String {
        return BuildConfig.HH_ACCESS_TOKEN
    }
}
