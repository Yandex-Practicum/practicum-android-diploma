package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.fields.DetailVacancyDto
import ru.practicum.android.diploma.data.dto.response.CountryResponse
import ru.practicum.android.diploma.data.dto.response.IndustryResponse
import ru.practicum.android.diploma.data.dto.response.RegionResponse

interface HhApi {

    @GET("/vacancies")
    suspend fun jobSearch(
        @QueryMap options: Map<String, String>
    ): SearchListDto

    @GET("vacancies/{vacancy_id}")
    suspend fun getDetailVacancy(@Path("vacancy_id") vacancyId: String): DetailVacancyDto

    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun similarVacancy(@Path("vacancy_id") vacancyId: String): SearchListDto

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: HHLiteJob/1.0(ya.tarannov@yandex.ru)"
    )
    @GET("areas/countries")
    suspend fun filterCountry(): CountryResponse

    @GET("areas/{area_id}")
    suspend fun filterRegion(@Path("area_id") areaId: String): RegionResponse

    @GET("areas/industries")
    suspend fun filterIndustry(): IndustryResponse
}
