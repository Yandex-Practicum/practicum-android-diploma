package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse

interface HHApiService {
    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap query: HashMap<String, String>
    ): VacancySearchResponse
}
