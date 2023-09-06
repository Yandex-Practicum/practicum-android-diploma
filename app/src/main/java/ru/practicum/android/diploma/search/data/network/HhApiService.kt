package ru.practicum.android.diploma.search.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.details.data.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.search.data.network.dto.CountryDto
import ru.practicum.android.diploma.search.data.network.dto.response.RegionCodeResponse
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse

interface HhApiService {
    @GET("/vacancies")
    suspend fun search(@Query("text") text: String): Response<VacanciesSearchCodeResponse>

    @GET("/areas")
    suspend fun getCountries(): Response<List<CountryDto>>


    @GET ("/areas/{area_id}")
    suspend fun getRegionInfo(@Path("area_id") areaId: String): Response<RegionCodeResponse>

    @GET("/vacancies/{id}")
    suspend fun searchDetails(@Path("id") id: String): Response<VacancyFullInfoModelDto>

    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(@Path("vacancy_id") vacancyId: String): Response<VacanciesSearchCodeResponse>

}



