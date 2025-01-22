package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.network.ApiResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): ApiResponse<Any>
}
