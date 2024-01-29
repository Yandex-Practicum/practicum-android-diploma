package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.data.dto.response.CountryResponse
import ru.practicum.android.diploma.data.dto.response.IndustryResponse
import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.data.dto.response.RegionResponse

interface HhApi {
    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun jobSearch(@Path("vacancy_id") vacancyId: String): VacancyResponse
    @GET("areas/countries")
    suspend fun filterCountry(): CountryResponse
    @GET("areas/{area_id}")
    suspend fun filterRegion(@Path("area_id") areaId: String): RegionResponse
    @GET("areas/industries")
    suspend fun filterIndustry(): IndustryResponse
}
