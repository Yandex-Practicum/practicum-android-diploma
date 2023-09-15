package ru.practicum.android.diploma.search.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.details.data.network.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.filter.data.model.CountryDto
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.filter.data.model.RegionListDto
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse

interface HhApiService {
    
    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap queryParams: Map<String, String>,
    ): Response<VacanciesResponse>
    
    @GET("/areas")
    suspend fun getAllCountries(): Response<List<CountryDto>>
    
    @GET("/industries")
    suspend fun getIndustries(): Response<List<IndustryDto>>
    
    @GET ("/areas/{area_id}")
    suspend fun getRegionInfo(@Path("area_id") areaId: String): Response<RegionListDto>

    @GET("/vacancies/{id}")
    suspend fun searchDetails(@Path("id") id: String): Response<VacancyFullInfoModelDto>

    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(@Path("vacancy_id") vacancyId: String): Response<VacanciesResponse>
}



