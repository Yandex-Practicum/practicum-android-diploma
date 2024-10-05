package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse

interface HHApiService {
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap expression: HashMap<String, String>
    ): VacancySearchResponse
}
