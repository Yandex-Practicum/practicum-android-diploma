package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailResponse
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesRemote
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse

interface JobVacancySearchApi {

    // Запрос списка вакансий
    @Headers(HEADER_AUTH, HEADER_USER)
    @GET("/vacancies")
    suspend fun getVacancyList(@QueryMap options: Map<String, String>): VacanciesRemote

    // Запрос списка вакансий
    @Headers(HEADER_AUTH, HEADER_USER)
    @GET("/vacancies")
    suspend fun getVacancyListString(@QueryMap options: Map<String, String>): VacanciesSearchResponse

    // Запрос детальной информации о вакансии
    @Headers(HEADER_AUTH, HEADER_USER)
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetail(@Path("vacancy_id") id: String): VacanciesSearchResponse

    companion object {
        const val HEADER_AUTH = "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
        const val HEADER_USER = "HH-User-Agent: practicum-android-diploma (makss.impeks@gmail.com)"
    }
}
