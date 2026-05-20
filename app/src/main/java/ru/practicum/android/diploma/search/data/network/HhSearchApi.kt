package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.SearchResponseDto

interface HhSearchApi {
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap params: Map<String, String>): SearchResponseDto
}
