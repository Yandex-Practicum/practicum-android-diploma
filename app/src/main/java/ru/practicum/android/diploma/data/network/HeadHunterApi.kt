package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface HeadHunterApi {
    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}", "User-Agent: DreamJob (shahidow@mail.com)")
    @GET("vacancies")
    suspend fun getVacancies(
        @QueryMap filters: Map<String, String>
    ): Response<VacancyResponse>

    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}", "User-Agent: DreamJob (shahidow@mail.com)")
    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(
        @Path("id") id: String
    ): Response<VacancyDetails>
}
