package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.request.VacancyDetailedRequest
import ru.practicum.android.diploma.data.dto.respone.Response

interface NetworkClient {

    suspend fun doRequest(
        dto: Any
    ): Response

    suspend fun getDetailVacancy(dto: VacancyDetailedRequest): Response
}
