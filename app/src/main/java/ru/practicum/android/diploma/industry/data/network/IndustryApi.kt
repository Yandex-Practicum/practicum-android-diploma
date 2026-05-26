package ru.practicum.android.diploma.industry.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.industry.data.dto.IndustriesDto

interface IndustryApi {
    @GET("industries")
    suspend fun getIndustries(): IndustriesDto
}
