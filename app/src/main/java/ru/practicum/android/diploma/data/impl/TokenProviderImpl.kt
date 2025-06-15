package ru.practicum.android.diploma.data.impl

import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.network.TokenProvider

class TokenProviderImpl : TokenProvider {
    override fun getAccessToken(): String {
        return BuildConfig.HH_ACCESS_TOKEN
    }
}
