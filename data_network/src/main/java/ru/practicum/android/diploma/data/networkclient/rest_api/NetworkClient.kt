package ru.practicum.android.diploma.data.networkclient.rest_api

import ru.practicum.android.diploma.data.networkclient.rest_api.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
