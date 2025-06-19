package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.Response

interface NetworkClientInterface {
    suspend fun doRequest(dto: Any): Response
}
