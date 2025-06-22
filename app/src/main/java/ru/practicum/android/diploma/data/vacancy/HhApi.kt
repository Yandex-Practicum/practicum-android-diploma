package ru.practicum.android.diploma.data.vacancy

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.AreasDto
import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.data.vacancy.models.SearchVacanciesDto
import ru.practicum.android.diploma.data.vacancy.models.VacancyDetailsDto

interface HhApi {
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): SearchVacanciesDto

    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(@Path("id") id: String): VacancyDetailsDto

    @GET("areas")
    suspend fun getAreas(): List<AreasDto>

    @GET("industries")
    suspend fun getIndustries(): List<IndustriesDto>
}
