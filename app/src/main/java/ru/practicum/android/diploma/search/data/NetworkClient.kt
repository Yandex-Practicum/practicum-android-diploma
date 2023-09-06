package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
    suspend fun getVacancyById(dto: Any): Response
}