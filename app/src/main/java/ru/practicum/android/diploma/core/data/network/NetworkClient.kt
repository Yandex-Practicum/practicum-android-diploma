package ru.practicum.android.diploma.core.data.network

interface NetworkClient {
    suspend fun doRequest(request: Request): Response
}
