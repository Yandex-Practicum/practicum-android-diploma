package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.search.data.model.Response
import ru.practicum.android.diploma.search.data.model.VacancyRequest

interface NetworkClient {
    suspend fun getVacancies(
        vacancyRequest: VacancyRequest
    ): Response

    suspend fun getVacancyDetails(id: String): Response
}
