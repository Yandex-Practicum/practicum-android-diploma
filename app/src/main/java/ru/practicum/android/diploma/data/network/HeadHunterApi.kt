package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.dto.VacancyDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface HeadHunterApi {
    @Headers("User-Agent: YourAppName (nyud91@gmail.com)")
    @GET("vacancies")
    suspend fun getVacancies(
        @Header("Authorization") token: String,
        @QueryMap filters: Map<String, String>
    ): Response<VacancyResponse>

    @Headers("User-Agent: YourAppName (nyud91@gmail.com)")
    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<VacancyDetails>
}
