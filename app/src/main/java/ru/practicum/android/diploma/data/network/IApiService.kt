package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsResponse
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse

interface IApiService {
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): Response<SearchVacanciesResponse>

    @GET("/areas")
    suspend fun getAreas(@Query("locale") locale: String): Response<ArrayList<AreaDto>>

    @GET("/industries")
    suspend fun getIndustries(@Query("locale") locale: String): Response<ArrayList<IndustryDto>>

    @GET("/vacancies/{id}")
    suspend fun getVacancyDetails(id: String): Response<GetVacancyDetailsResponse>
}
