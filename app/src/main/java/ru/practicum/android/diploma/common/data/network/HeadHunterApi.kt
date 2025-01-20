package ru.practicum.android.diploma.common.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.common.data.dto.SearchVacancyResponse

interface HeadHunterApi{
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): SearchVacancyResponse

}
