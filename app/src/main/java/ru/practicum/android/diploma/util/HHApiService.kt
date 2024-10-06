package ru.practicum.android.diploma.util

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.network.VacancySearchResponse
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse

interface HHApiService {
    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}")
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap query: HashMap<String, String>
    ): VacancySearchResponse

    @GET("vacancies/{vacancyId}")
    suspend fun getVacancyDetails(@Path("vacancyId") vacancyId: String): VacancyDetailsResponse
}
