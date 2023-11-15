package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.detail.FullVacancyDto
import ru.practicum.android.diploma.data.dto.filter.CountryDto
import ru.practicum.android.diploma.data.dto.filter.IndustryDto
import ru.practicum.android.diploma.data.dto.filter.RegionListDto
import ru.practicum.android.diploma.data.dto.search.SearchResponse
import ru.practicum.android.diploma.data.dto.similar.SearchSimilarResponse

interface ApiService {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: EmployMe (eenot84@yandex.ru)"
    )
    @GET("/vacancies")
    suspend fun search(@QueryMap options: Map<String, String>): SearchResponse

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: EmployMe (eenot84@yandex.ru)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): FullVacancyDto

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: EmployMe (eenot84@yandex.ru)"
    )
    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun searchSimilar(
        @Path("vacancy_id") id: String,
    ): SearchSimilarResponse

    @GET("/areas/countries")
    suspend fun getCountres(): List<CountryDto>

    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>
    @GET("/areas/{area_id}")
    suspend fun getRegionInfo(@Path("area_id") areaId: String): RegionListDto
}
