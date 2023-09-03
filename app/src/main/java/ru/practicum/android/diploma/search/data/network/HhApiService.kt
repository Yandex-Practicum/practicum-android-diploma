package ru.practicum.android.diploma.search.data.network

import kotlinx.serialization.json.JsonArray
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.network.dto.CountryDto
import ru.practicum.android.diploma.search.data.network.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.search.data.network.dto.response.CountriesResponse

interface HhApiService {
    @GET("/vacancies")
    suspend fun search(
        @Query("text") text: String): Response<VacanciesSearchResponse>

    @GET("/areas/countries")
    suspend fun getCountries(): Response<List<CountryDto>>

}



