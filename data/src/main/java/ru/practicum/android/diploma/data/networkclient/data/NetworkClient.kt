package ru.practicum.android.diploma.data.networkclient.data

import ru.practicum.android.diploma.data.networkclient.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
