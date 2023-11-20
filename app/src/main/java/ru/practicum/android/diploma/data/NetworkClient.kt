package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.domain.models.filter.Filters

interface NetworkClient {
    suspend fun doSearchRequest(text: String, filters: Filters, pageCount: Int): Response
    suspend fun doCountryRequest(): Response
    suspend fun doDetailRequest(id: String): Response
    suspend fun doAreaRequest(id: String): Response
    suspend fun doSearchSimilarRequest(id: String): Response
    suspend fun doIndustryRequest(): Response
}