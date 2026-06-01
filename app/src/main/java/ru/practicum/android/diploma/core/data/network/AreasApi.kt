package ru.practicum.android.diploma.core.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.core.data.dto.AreaDto

interface AreasApi {
    @GET("areas")
    suspend fun getAreas(): List<AreaDto>
}
