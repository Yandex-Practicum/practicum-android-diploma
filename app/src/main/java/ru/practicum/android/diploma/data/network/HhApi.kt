package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.practicum.android.diploma.data.dto.CountryResponse
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.dto.JobResponse
import ru.practicum.android.diploma.data.dto.RegionResponse

interface HhApi {
    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun jobSearch(@Path("vacancy_id") vacancyId: String): JobResponse
    @GET("areas/countries")
    suspend fun filterCountry(): CountryResponse
    @GET("areas/{area_id}")
    suspend fun filterRegion(@Path("area_id") areaId: String): RegionResponse
    @GET("areas/industries")
    suspend fun filterIndustry(): IndustryResponse
}

// добавить запрос выбора страны
// выбор региона
// выбор места работы
