package ru.practicum.android.diploma.search.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface HeadHunterAPI {
    // Поиск вакансий
    @GET("vacancies")
    fun searchVacancies(
        @Header("Authorization") authToken: String?,
        @Header("User-Agent") userAgent: String?,
        @QueryMap options: Map<String?, String?>?
    ): Call<SearchResponse?>?
}
