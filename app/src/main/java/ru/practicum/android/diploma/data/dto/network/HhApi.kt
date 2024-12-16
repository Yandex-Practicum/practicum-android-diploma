package ru.practicum.android.diploma.data.dto.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.model.VacancyDto

interface HhApi {

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyById(@Path("vacancy_id") vacancyId: String): VacancyDto

    @GET("/vacancies")
    suspend fun getVacancies(@Query("query") searchQuery: String): VacancySearchResponse
}
