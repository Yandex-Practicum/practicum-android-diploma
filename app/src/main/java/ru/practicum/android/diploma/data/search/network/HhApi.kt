package ru.practicum.android.diploma.data.search.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.fields.CountryDto
import ru.practicum.android.diploma.data.dto.fields.DetailVacancyDto
import ru.practicum.android.diploma.data.response.AreaResponse
import ru.practicum.android.diploma.data.response.CountryResponse
import ru.practicum.android.diploma.data.response.IndustryResponse
import ru.practicum.android.diploma.data.response.RegionResponse
import ru.practicum.android.diploma.domain.models.Industry

interface HhApi {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: HHLiteJob/1.0(ya.tarannov@yandex.ru)"
    )

    @GET("vacancies")
    suspend fun jobSearch(
        @QueryMap options: Map<String, String>
    ): SearchListDto

    @GET("vacancies/{vacancy_id}")
    suspend fun getDetailVacancy(@Path("vacancy_id") vacancyId: String): DetailVacancyDto

    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun similarVacancy(@Path("vacancy_id") vacancyId: String): SearchListDto

    @GET("areas")
    suspend fun filterArea(): AreaResponse
    @GET("areas/countries")
    suspend fun filterCountry(): List<CountryDto>

    //@GET("areas/{area_id}")
    //suspend fun filterRegion(@Path("area_id") areaId: String): RegionResponse

    @GET("industries")
    suspend fun filterIndustry(): List<Industry>
}
