package ru.practicum.android.diploma.core.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

import ru.practicum.android.diploma.core.data.network.dto.AreasDto
import ru.practicum.android.diploma.core.data.network.dto.CountryDto
import ru.practicum.android.diploma.core.data.network.dto.DetailVacancyResponse
import ru.practicum.android.diploma.core.data.network.dto.IndustryDto
import ru.practicum.android.diploma.core.data.network.dto.SearchVacanciesResponse

interface HhApi {
    @GET("vacancies")
    suspend fun getVacancies(@QueryMap queryMap: Map<String, String>): Response<SearchVacanciesResponse>

    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: Long): Response<DetailVacancyResponse>

    @GET("industries")
    suspend fun getIndustries(): Response<List<IndustryDto>>

    @GET("/areas/countries")
    suspend fun getCountries(): Response<List<CountryDto>>

    @GET("areas")
    suspend fun getAreas(): Response<List<AreasDto>>

    @GET("areas/{area_id}")
    suspend fun getAreasById(@Path("vacancy_id") id: String): Response<List<AreasDto>>

}

enum class HhApiQuery(val value: String) {
    SEARCH_TEXT("text"),
    PAGE("page"),
    PER_PAGE("per_page"),
    INDUSTRY_FILTER("industry"),
    SALARY_FILTER("salary"),
    IS_ONLY_WITH_SALARY_FILTER("only_with_salary"),
    REGION_FILTER("area")
}
