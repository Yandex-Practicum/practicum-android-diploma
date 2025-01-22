package ru.practicum.android.diploma.common.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.common.data.dto.SearchVacancyResponse

interface HeadHunterApi {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Practicum-Android-Diploma (danil.ciplenkov@yandex.ru)"
    )
    @GET("vacancies")
    suspend fun searchVacancies(@QueryMap options: Map<String, String>): SearchVacancyResponse

}
