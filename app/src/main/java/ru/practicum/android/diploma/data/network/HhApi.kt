package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacancySearchResponse

interface HhApi {
    @GET("vacancies")
    suspend fun searchVacancies(
        @Query("text") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): Response<VacancySearchResponse>
}
