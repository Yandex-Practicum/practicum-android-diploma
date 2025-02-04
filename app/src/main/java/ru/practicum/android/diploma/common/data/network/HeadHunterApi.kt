package ru.practicum.android.diploma.common.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.common.data.dto.SearchVacancyResponse
import ru.practicum.android.diploma.common.data.dto.region.SearchRegionResponse
import ru.practicum.android.diploma.filter.data.dto.model.AreaDto
import ru.practicum.android.diploma.filter.data.dto.model.CountryDto
import ru.practicum.android.diploma.filter.data.dto.model.IndustryDto
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse

interface HeadHunterApi {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Practicum-Android-Diploma (danil.ciplenkov@yandex.ru)"
    )
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): SearchVacancyResponse

    @GET("vacancies/{vacancyId}")
    suspend fun getVacancyDetails(@Path("vacancyId") vacancyId: String): VacancyDetailsResponse

    @GET("industries")
    suspend fun getIndustries(): List<IndustryDto> // IndustriesResponse

    @GET("areas")
    suspend fun getCountries(): List<CountryDto> // CountriesResponse

    @GET("areas/{area_id}")
    suspend fun searchRegionsById(@Path("area_id") areaId: String): SearchRegionResponse

    @GET("areas")
    suspend fun getAllRegions(): List<AreaDto>
}
