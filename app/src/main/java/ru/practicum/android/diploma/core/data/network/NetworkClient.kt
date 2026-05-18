package ru.practicum.android.diploma.core.data.network

interface NetworkClient {
    suspend fun doRequest(dtoRequest : Any)
}
