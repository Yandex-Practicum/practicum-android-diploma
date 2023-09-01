package ru.practicum.android.diploma.search.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.network.dto.VacanciesSearchResponse

interface HhApiService {
    @GET("/vacancies")
    suspend fun search(
        @Query("text") text: String): Response<VacanciesSearchResponse>
}



