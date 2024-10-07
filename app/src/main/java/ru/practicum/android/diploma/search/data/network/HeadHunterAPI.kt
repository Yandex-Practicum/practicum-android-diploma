package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.Industry
import ru.practicum.android.diploma.search.data.dto.SearchResponse

interface HeadHunterAPI {
    // Поиск вакансий
    @GET("vacancies")
    suspend fun searchVacancies(
        @Header("Authorization") authToken: String?,
        @Header("User-Agent") userAgent: String?,
        @QueryMap options: Map<String, String>
    ): SearchResponse

    // Получение списка всех отраслей
    @GET("industries")
    suspend fun getIndustries(): List<Industry>
}
