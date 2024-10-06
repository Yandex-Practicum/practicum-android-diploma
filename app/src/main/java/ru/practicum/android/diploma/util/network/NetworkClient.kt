package ru.practicum.android.diploma.util.network

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
