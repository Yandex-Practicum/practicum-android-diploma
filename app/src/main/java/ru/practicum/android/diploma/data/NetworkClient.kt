package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    suspend fun filterAreaRequest(dto: Any): Response
}
