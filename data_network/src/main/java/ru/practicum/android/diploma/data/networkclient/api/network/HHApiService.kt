package ru.practicum.android.diploma.data.networkclient.api.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.BuildConfig
import ru.practicum.android.diploma.data.networkclient.api.dto.HHIndustriesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HHRegionsResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacanciesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacancyDetailResponse

internal interface HHApiService {
    @Headers("HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap options: Map<String, String>,
        @Header("Authorization") token: String = "Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    ): HHVacanciesResponse

    @Headers("HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/industries")
    suspend fun searchIndustries(
        @QueryMap options: Map<String, String>?,
        @Header("Authorization") token: String = "Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    ): HHIndustriesResponse

    @Headers("HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/areas")
    suspend fun searchRegions(
        @QueryMap options: Map<String, String>?,
        @Header("Authorization") token: String = "Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    ): HHRegionsResponse

    @Headers("HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(
        @Path("vacancy_id") vacancyId: String,
        @Header("Authorization") token: String = "Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    ): HHVacancyDetailResponse
}
