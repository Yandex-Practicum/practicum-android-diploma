package ru.practicum.android.diploma.search.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.details.data.network.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.filter.data.model.CountryDto
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.filter.data.model.RegionListDto
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse

interface HhApiService {
    @NewResponse
    @GET("/vacancies")
    suspend fun searchVacancies(
        @QueryMap queryParams: Map<String, String>,
    ): Response<VacanciesResponse>
    
    @NewResponse
    @GET("/areas")
    suspend fun getAllCountries(): Response<List<CountryDto>>
    
    @NewResponse
    @GET("/industries")
    suspend fun getIndustries(): Response<List<IndustryDto>>
    
    @GET("/vacancies")
    suspend fun search(@Query("text") text: String): Response<VacanciesSearchCodeResponse>

    @GET ("/areas/{area_id}")
    suspend fun getRegionInfo(@Path("area_id") areaId: String): Response<RegionListDto>

    @NewResponse
    @GET("/vacancies/{id}")
    suspend fun searchDetails(@Path("id") id: String): Response<VacancyFullInfoModelDto>

    @NewResponse
    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(@Path("vacancy_id") vacancyId: String): Response<VacanciesSearchCodeResponse>
}



