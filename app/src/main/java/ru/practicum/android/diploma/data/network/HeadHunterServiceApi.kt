package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.respone.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.respone.VacancyDetailedResponse

interface HeadHunterServiceApi {

    @GET("/vacancies")
    suspend fun searchVacancies(
        @Query("text") name: String,
        @Query("page") page: Long,
        @Query("per_page") amount: Long
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
