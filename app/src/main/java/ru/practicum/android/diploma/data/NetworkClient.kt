package ru.practicum.android.diploma.data

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response

    suspend fun doRequestFilter(dto: Any): Response
}
