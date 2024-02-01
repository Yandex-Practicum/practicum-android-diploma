package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.response.CountryResponse
import ru.practicum.android.diploma.data.dto.response.IndustryResponse
import ru.practicum.android.diploma.data.dto.response.JobResponse
import ru.practicum.android.diploma.data.dto.response.RegionResponse

interface HhApi {
    @GET("vacancies")
    suspend fun jobSearch(
        @Query("term") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): JobResponse
    @GET("areas/countries")
    suspend fun filterCountry(): CountryResponse
    @GET("areas/{area_id}")
    suspend fun filterRegion(@Path("area_id") areaId: String): RegionResponse
    @GET("areas/industries")
    suspend fun filterIndustry(): IndustryResponse
}
