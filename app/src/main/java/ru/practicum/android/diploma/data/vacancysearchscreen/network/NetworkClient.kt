package ru.practicum.android.diploma.data.vacancysearchscreen.network

import ru.practicum.android.diploma.data.models.vacancies.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}
