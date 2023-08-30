package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.dto.SearchResponse

interface Api {

    @GET("/vacancies?text=text")
    suspend fun search(@Query("term") text: String): SearchResponse
}