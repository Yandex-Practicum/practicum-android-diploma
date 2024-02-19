package ru.practicum.android.diploma.commons.data.network

import ru.practicum.android.diploma.commons.data.dto.Response
import ru.practicum.android.diploma.commons.data.dto.detailed.VacancyDetailedRequest

interface NetworkClient {

    suspend fun doRequest(
        dto: Any
    ): Response

    suspend fun getDetailVacancy(dto: VacancyDetailedRequest): Response
}
