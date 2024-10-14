package ru.practicum.android.diploma.data.networkclient.api.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.BuildConfig
import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.HHIndustriesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.response.regions.HHRegionsResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.HHVacanciesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse

private const val HH_HEADER_ONE = "HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)"
private const val HH_TOKEN = "Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
internal interface HHApiService {
    @Headers(HH_HEADER_ONE)
    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap options: Map<String, String>,
        @Header("Authorization") token: String = HH_TOKEN
    ): HHVacanciesResponse

    @Headers(HH_HEADER_ONE)
    @GET("/industries")
    suspend fun searchIndustries(): HHIndustriesResponse

    @GET("/areas")
    suspend fun searchRegions(): HHRegionsResponse

    @Headers(HH_HEADER_ONE)
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(
        @Path("vacancy_id") vacancyId: String,
        @Header("Authorization") token: String = HH_TOKEN
    ): HHVacancyDetailResponse
}
