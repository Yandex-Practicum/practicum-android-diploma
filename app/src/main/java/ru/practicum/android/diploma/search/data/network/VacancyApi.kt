package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto

interface VacancyApi {
    @GET("/areas")
    suspend fun getAreas(): List<FilterAreaDto>

    @GET("/industries")
    suspend fun getIndustry(): List<FilterIndustryDto>

    @GET("/vacancies")
    suspend fun getVacancies(): VacancyResponseDto

    @GET("vacancies/{id}")
    suspend fun getVacancyById(
        @Path("id") id: String
    ): VacancyDetailDto
}
