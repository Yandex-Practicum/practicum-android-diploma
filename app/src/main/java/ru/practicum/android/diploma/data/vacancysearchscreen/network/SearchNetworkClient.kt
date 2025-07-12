package ru.practicum.android.diploma.data.vacancysearchscreen.network

import ru.practicum.android.diploma.data.models.vacancies.Response

interface SearchNetworkClient {
    suspend fun doRequest(dto: Any): Response
}
