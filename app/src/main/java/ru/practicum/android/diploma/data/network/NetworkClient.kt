package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Request
import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Request): Response
}
