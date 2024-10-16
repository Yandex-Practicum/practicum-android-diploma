package ru.practicum.android.diploma.util.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.filters.industries.data.dto.IndustryDto
import ru.practicum.android.diploma.search.data.network.VacancySearchResponse
import ru.practicum.android.diploma.vacancy.data.dto.AreaDto
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse

interface HHApiService {
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap query: HashMap<String, String>
    ): VacancySearchResponse

    @GET("vacancies/{vacancyId}")
    suspend fun getVacancyDetails(@Path("vacancyId") vacancyId: String): VacancyDetailsResponse

    @GET("areas")
    suspend fun getRegions(): List<AreaDto>

    @GET("industries")
    suspend fun getIndustries(): List<IndustryDto>

    @GET("area_leaves")
    suspend fun searchRegionsByName(@QueryMap textAndId: HashMap<String, String>): SearchRegionsByNameResponse
}
