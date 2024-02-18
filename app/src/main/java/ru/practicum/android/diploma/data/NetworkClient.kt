package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {

    suspend fun executeNetworkRequest(dto: Any): Response
}
