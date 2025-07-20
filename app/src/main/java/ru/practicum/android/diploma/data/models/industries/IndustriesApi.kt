package ru.practicum.android.diploma.data.models.industries

import retrofit2.http.GET
import retrofit2.http.Headers

interface IndustriesApi {
    @Headers("HH-User-Agent: WorkNest/1.0 (danilov-av2004@mail.ru)")
    @GET("/industries")
    suspend fun getIndustries(): List<IndustryGroupDto>
}
