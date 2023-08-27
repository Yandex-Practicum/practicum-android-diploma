package ru.practicum.android.diploma.search.data.network

interface NetworkClient {
    suspend fun doRequest(any: Any): Response
}