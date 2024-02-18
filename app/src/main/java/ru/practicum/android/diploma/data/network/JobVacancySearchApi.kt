package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse

interface JobVacancySearchApi {

    @GET()
    suspend fun search(@Query("term") text: String): VacanciesSearchResponse
}
