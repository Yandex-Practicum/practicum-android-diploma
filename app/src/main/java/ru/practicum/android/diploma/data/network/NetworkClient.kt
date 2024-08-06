package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Responce

interface NetworkClient {
    suspend fun doRequest(dto: Any): Responce
}
