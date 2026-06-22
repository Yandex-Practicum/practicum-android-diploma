package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.FilterAreaResponse
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyDto

interface PracticumApiService {
    @GET("areas")
    suspend fun getAreas(): FilterAreaResponse

    @GET("vacancies")
    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("page") page: Int
    ): VacanciesResponse

    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id") vacancyId: String): VacancyDto
}
