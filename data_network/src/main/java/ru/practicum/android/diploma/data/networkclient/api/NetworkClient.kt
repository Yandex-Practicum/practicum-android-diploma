package ru.practicum.android.diploma.data.networkclient.api

import ru.practicum.android.diploma.data.networkclient.api.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
