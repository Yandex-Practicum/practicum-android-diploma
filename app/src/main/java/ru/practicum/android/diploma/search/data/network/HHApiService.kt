package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse

interface HHApiService {
    @GET("vacancies")
    suspend fun searchVacancies(
        @Header("Authorization: Bearer ") accessToken: String,
        @QueryMap query: HashMap<String, String>
    ): VacancySearchResponse
}
