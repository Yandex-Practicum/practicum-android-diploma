package ru.practicum.android.diploma.data.network

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
