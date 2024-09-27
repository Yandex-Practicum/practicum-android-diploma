package ru.practicum.android.diploma.networkclient.data

import ru.practicum.android.diploma.networkclient.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
