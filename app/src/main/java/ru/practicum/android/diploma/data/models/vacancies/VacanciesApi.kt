package ru.practicum.android.diploma.data.models.vacancies

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface VacanciesApi {
    @Headers("HH-User-Agent: WorkNest/1.0 (danilov-av2004@mail.ru)")
    @GET("vacancies")
    suspend fun getVacancies(
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 20,
        @Query("text") text: String,
        @QueryMap filter: Map<String, String>,
    ): VacanciesResponseDto
}
