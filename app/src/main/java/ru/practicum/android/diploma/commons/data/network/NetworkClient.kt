package ru.practicum.android.diploma.commons.data.network

import ru.practicum.android.diploma.commons.data.dto.Response

interface NetworkClient {

    suspend fun doRequest(
        dto: Any
    ): Response

}
