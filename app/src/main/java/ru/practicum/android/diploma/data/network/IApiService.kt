package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Industry
import ru.practicum.android.diploma.data.network.dto.GetVacancyDetailsResponse
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse

interface IApiService {
    @GET("/vacancies")
    fun searchVacancies(@QueryMap options: Map<String, String>): SearchVacanciesResponse

    @GET("/areas")
    fun getAreas(@Query("locale") locale: String): ArrayList<Area>

    @GET("/industries")
    fun getIndustries(@Query("locale") locale: String): ArrayList<Industry>

    @GET("/vacancies/{id}")
    fun getVacancyDetails(id: String): GetVacancyDetailsResponse
}
