package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface HeadHunterAPI {
    // Поиск вакансий
    @GET("vacancies/{vacancy_id}")
    suspend fun searchVacancies(
        @Header("Authorization") authToken: String?,
        @Header("User-Agent") userAgent: String?,
        @QueryMap options: Map<String, String>
    ): SearchResponseVacancy

    // Получение списка всех отраслей
    @GET("industries")
    suspend fun getIndustries(): List<Industry>
}
