package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.FullVacancyDto
import ru.practicum.android.diploma.data.dto.SearchResponse

interface ApiService {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: EmployMe (eenot84@yandex.ru)"
    )
    @GET("/vacancies")
    suspend fun search(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchResponse

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: EmployMe (eenot84@yandex.ru)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): FullVacancyDto
}
