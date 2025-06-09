package ru.practicum.android.diploma.data.network

interface TokenProvider {
    fun getAccessToken(): String
}
