package ru.practicum.android.diploma.data.network.api

import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.main.VacancyLongDto
import ru.practicum.android.diploma.data.dto.response.VacancySearchResponseDto
import ru.practicum.android.diploma.data.network.Response

interface NetworkClient {

    suspend fun searchVacancies(@QueryMap filters: Map<String, String>): Response<VacancySearchResponseDto>
    suspend fun getVacancyDetails(id: String): Response<VacancyLongDto>
}
