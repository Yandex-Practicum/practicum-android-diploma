package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Industry
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsResponse
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse

interface IApiService {
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): Response<SearchVacanciesResponse>

    @GET("/areas")
    suspend fun getAreas(@Query("locale") locale: String): Response<ArrayList<Area>>

    @GET("/industries")
    suspend fun getIndustries(@Query("locale") locale: String): Response<ArrayList<Industry>>

    @GET("/vacancies/{id}")
    suspend fun getVacancyDetails(id: String): Response<GetVacancyDetailsResponse>
}
