package ru.practicum.android.diploma.networkclient.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.networkclient.data.dto.HHIndustriesResponse
import ru.practicum.android.diploma.networkclient.data.dto.HHRegionsResponse
import ru.practicum.android.diploma.networkclient.data.dto.HHVacanciesResponse
import ru.practicum.android.diploma.networkclient.data.dto.HHVacancyDetailResponse

interface HHApiService {
    @Headers("Autorization: Bearer TODO TOKEN", "HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap options: Map<String, String>,
    ): HHVacanciesResponse

    @Headers("Autorization: Bearer TODO TOKEN", "HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/industries")
    suspend fun searchIndustries(
        @QueryMap options: Map<String, String>?,
    ): HHIndustriesResponse

    @Headers("Autorization: Bearer TODO TOKEN", "HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/areas")
    suspend fun searchRegions(
        @QueryMap options: Map<String, String>?,
    ): HHRegionsResponse

    @Headers("Autorization: Bearer TODO TOKEN", "HH-User-Agent: CareerRepository/1.0 (zpe25@yandex.ru)")
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") vacancyId: String): HHVacancyDetailResponse
}
