package ru.practicum.android.diploma.data.vacancy

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.vacancy.models.SearchVacanciesDto

interface HhApi {
    @GET("/vacancies")
    suspend fun searchVacancies(@Query("text") text: String): SearchVacanciesDto
}
