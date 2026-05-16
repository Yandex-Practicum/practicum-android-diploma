package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailDto

interface ApiService {
    @GET("/areas")
    suspend fun getAreas(): Response<List<FilterAreaDto>>

    @GET("/industries")
    suspend fun getIndustries(): Response<List<FilterIndustryDto>>

    @GET("/vacancies")
    suspend fun getVacancies(
        @QueryMap options: Map<String, String>
    ): Response<VacanciesResponse>

    @GET("/vacancies/{id}")
    suspend fun getVacancyDetails(
        @Path("id") id: String
    ): Response<VacancyDetailDto>
}
