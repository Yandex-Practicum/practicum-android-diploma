package ru.practicum.android.diploma.data.search.network

interface NetworkClient {
    suspend fun search(dto: JobSearchRequest): Response
    suspend fun doRequest(dto: Any): Response
}
