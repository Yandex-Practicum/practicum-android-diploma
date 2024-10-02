package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.VacancySearchResponse

interface HHApiService {
    @GET("vacancies?text={expression}")
    suspend fun searchVacancies(@Query("expression") expression: String): VacancySearchResponse
}
