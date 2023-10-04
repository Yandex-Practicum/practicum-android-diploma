package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.data.network.dto.feilds.CountryDto

interface RetrofitApi {

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: WorkHub (valhero@yandex.ru)"
    )

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): Response

    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(@Path("vacancy_id") id: String): Response

    @GET("/vacancies")
    suspend fun getVacanciesWithFilter(@QueryMap filters:Map<String, String>): Response

    @GET("/areas")
    suspend fun getCountries(): List<CountryDto>
}