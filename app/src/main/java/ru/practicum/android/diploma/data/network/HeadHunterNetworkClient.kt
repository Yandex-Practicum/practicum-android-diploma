package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface HeadHunterNetworkClient {
    suspend fun getVacancies(filters: Map<String, String>): Response<VacancyResponse>
    suspend fun getVacancy(id: String): Response<VacancyDetails>
}
