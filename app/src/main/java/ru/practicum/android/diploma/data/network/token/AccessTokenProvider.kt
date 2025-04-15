package ru.practicum.android.diploma.data.network.token

interface AccessTokenProvider {
    fun getAccessToken(): String
    fun saveAccessToken(token: String)
    fun clearAccessToken()
}
