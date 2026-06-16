package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.data.dto.FilterAreaResponse

interface PracticumApiService {
    @GET("/areas")
    suspend fun getAreas(): FilterAreaResponse
}
