package ru.practicum.android.diploma.data.dto.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.model.VacancyDto

const val TOKEN = BuildConfig.HH_ACCESS_TOKEN
const val EMAIL = "someEmail"
const val APPLICATION_NAME = "applicationName"

interface HhApi {

    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyById(@Path("vacancy_id") vacancyId: String): VacancyDto

    @GET("/vacancies")
    suspend fun getVacancies(@Query("text") vacancyName: String): Response
}
