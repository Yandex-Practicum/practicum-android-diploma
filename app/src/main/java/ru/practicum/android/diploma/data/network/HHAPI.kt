package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface HHAPI {

    @Headers("Authorization: Bearer YOUR_TOKEN",
        "HH-User-Agent: Application Name (name@example.com)")
    @GET("search/cafes")
    suspend fun searchCafes(@QueryMap options: Map<String, String>): List<Cafe>
}
