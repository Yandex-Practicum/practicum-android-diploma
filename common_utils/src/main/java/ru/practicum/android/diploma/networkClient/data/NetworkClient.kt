package ru.practicum.android.diploma.networkClient.data

import ru.practicum.android.diploma.networkClient.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
