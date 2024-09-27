package ru.practicum.android.diploma.network_client.data

import ru.practicum.android.diploma.network_client.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
