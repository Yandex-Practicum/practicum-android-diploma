package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.model.VacanciesResponse

interface HHApi {

    @GET("vacancies")
    suspend fun getVacancyByName(
        @QueryMap filters: Map<String, String>
    ): VacanciesResponse

}
