package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse

interface JobVacancySearchApi {

    @Headers("Authorization: Bearer YOUR_TOKEN",
        "HH-User-Agent: Application Name (name@example.com)")
    @GET("/vacancies")
    suspend fun search(@Query("term") text: String): VacanciesSearchResponse
}
