package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.network.test.TracksSearchResponse


interface HhApiService {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String) : TracksSearchResponse
}

