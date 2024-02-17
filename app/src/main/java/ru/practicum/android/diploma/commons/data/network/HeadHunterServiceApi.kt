package ru.practicum.android.diploma.commons.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.commons.data.dto.detailed.VacancyDetailedResponse
import ru.practicum.android.diploma.commons.data.dto.search.VacanciesSearchResponse

interface HeadHunterServiceApi {

    @GET("/vacancies")
    suspend fun searchVacancies(
        @Query("text") name: String,
        @Query("page") page: Long,
        @Query("per_page") amount: Int
    ): VacanciesSearchResponse

    @GET("/vacancies/{id}/similar_vacancies")
    suspend fun searchSimilarVacancies(
        @Path("id") id: String,
        @Query("page") page: Long,
        @Query("per_page") amount: Long
    ): VacanciesSearchResponse

    @GET("/vacancies/{id}")
    suspend fun searchConcreteVacancy(
        @Path("id") id: String
    ): VacancyDetailedResponse

}
