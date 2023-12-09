package ru.practicum.android.diploma.util.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.models.SearchResponse

interface HhApiVacancy {

    @GET("vacancies")
    fun getVacancyList(
        @Header(HEADER_AUTH) token: String,
        @QueryMap options: HashMap<String, String>
    ): SearchResponse

    // Тут свои запросы

    companion object {
        const val HEADER_AUTH = "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
    }
}
