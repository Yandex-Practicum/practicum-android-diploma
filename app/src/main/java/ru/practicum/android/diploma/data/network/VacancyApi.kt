package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface VacancyApi {
    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>

    @GET("/vacancies?")
    suspend fun searchVacancies(@QueryMap filters: Map<String, String>): VacancyResponse

    @GET("/vacancies/{id}")
    suspend fun getVacancyDetails(@Path("id") id: String): VacancyDto
}
