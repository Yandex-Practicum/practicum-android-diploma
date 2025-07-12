package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.model.VacanciesResponse

interface HHApi {

    @Headers(
        "Authorization: Bearer YOUR_TOKEN",
        "HH-User-Agent: Application Name (name@example.com)"
    )
    @GET("vacancies")
    suspend fun getVacancyByName(
        @QueryMap filters: Map<String, String>
    ): VacanciesResponse

}
