package ru.practicum.android.diploma.data.vacancy

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.vacancy.models.SearchVacanciesDto
import ru.practicum.android.diploma.data.vacancy.models.VacancyDetailsDto

interface HhApi {
    @GET("/vacancies")
    suspend fun searchVacancies(@Query("text") text: String): SearchVacanciesDto

    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(@Path("id") id: String): VacancyDetailsDto
}
