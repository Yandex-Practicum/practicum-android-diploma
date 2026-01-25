package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.data.dto.IndustryDTO

interface VacancyApi {
    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDTO>
}
