package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.vacancies.response.Response

interface NetworkClient {

    suspend fun doRequest(dto: Any): Response

    suspend fun doRequestFilter(dto: Any): Response
}
