package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.util.Resource

interface NetworkClient {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T>
}
