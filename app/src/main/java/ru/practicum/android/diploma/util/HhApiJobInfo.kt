package ru.practicum.android.diploma.util

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.models.JobSearchRequest
import ru.practicum.android.diploma.search.data.models.JobSearchResponseDto

interface HhApiJobInfo {
    @Headers(
        HEADER_AUTH,
        USER
    )
    @GET("vacancies")
    suspend fun getJobList(
        @QueryMap options: HashMap<String, String>
    ): JobSearchResponseDto

    // Ниже добавляйте свои ф-ии

    @GET("vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancies(@Path("vacancy_di")vacancyId: String) :
            JobSearchResponseDto

    companion object{
        const val HEADER_AUTH = "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}"
        const val USER = "HH-User-Agent: Diplom_Yandex_HH (alk68@yandex.ru)"
    }
}