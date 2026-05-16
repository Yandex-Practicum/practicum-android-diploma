package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.area.AreasResponse
import ru.practicum.android.diploma.data.dto.industry.IndustriesResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacanciesResponse
import ru.practicum.android.diploma.data.dto.vacancy.VacancyDetailsResponse

interface ApiService {
    @GET("/areas")
    suspend fun getAreas(
        @Header("Authorization") token: String
    ): AreasResponse

    @GET("/industries")
    suspend fun getIndustries(
        @Header("Authorization") token: String
    ): IndustriesResponse

    @GET("/vacancies")
    suspend fun getVacancies(
        @Header("Authorization") token: String,
        @QueryMap options: Map<String, String>
    ): VacanciesResponse

    @GET("/vacancies/{id}")
    suspend fun getVacancyDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): VacancyDetailsResponse
}
