package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.vacancy.vacancydetails.VacancyDetailsDto
import ru.practicum.android.diploma.data.dto.vacancy.vacanysearch.VacancySearchResponseDto

interface NetworkClient {
    suspend fun detailsVacancyRequest(requestId: String): Result<VacancyDetailsDto>
    suspend fun vacanciesSearchRequest(requestQuery: Map<String, String>): Result<VacancySearchResponseDto>
}
