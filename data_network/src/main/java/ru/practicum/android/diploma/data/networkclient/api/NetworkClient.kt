package ru.practicum.android.diploma.data.networkclient.api

import ru.practicum.android.diploma.commonutils.network.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
