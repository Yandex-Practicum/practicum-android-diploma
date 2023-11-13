package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    suspend fun doSearchRequest(options: HashMap<String, String>): Response

    suspend fun doCountryRequest(): Response
    suspend fun doDetailRequest(id: String): Response
    suspend fun doAreaRequest(id: String): Response
    suspend fun doSearchSimilarRequest(id: String): Response
}