package ru.practicum.android.diploma.search.data.network

interface NetworkClient {
    suspend fun doRequest(request: Any): CodeResponse

    suspend fun doCountryRequest(): CodeResponse
}