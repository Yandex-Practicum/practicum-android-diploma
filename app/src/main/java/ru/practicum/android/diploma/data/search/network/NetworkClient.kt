package ru.practicum.android.diploma.data.search.network

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
