package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.domain.models.Industry

interface VacancyApi {
    @GET("/industries")
    suspend fun getIndustries(): List<Industry>
}
