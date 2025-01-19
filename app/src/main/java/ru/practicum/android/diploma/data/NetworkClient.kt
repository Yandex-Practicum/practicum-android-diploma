package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.network.NetworkResult

interface NetworkClient {
    suspend fun doRequest(dto: Any): NetworkResult
}
