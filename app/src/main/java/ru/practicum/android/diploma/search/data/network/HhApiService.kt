package ru.practicum.android.diploma.search.data.network

import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.network.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.search.data.network.test.TracksSearchResponse


/* interface HhApiService {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): TracksSearchResponse
} */

interface HhApiService {
    @GET("/vacancies")
    suspend fun search(
        @Query("text") text: String): Response<VacanciesSearchResponse>
}



