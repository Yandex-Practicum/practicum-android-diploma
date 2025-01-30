package ru.practicum.android.diploma.common.data.network

interface NetworkClient {
    suspend fun doRequest(dto: Any): Any
}
