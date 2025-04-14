package ru.practicum.android.diploma.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

@Suppress("TooGenericExceptionCaught")
suspend fun <T> handleRequest(request: suspend () -> Response<T>): Response<T> {
    return withContext(Dispatchers.IO) {
        try {
            request.invoke()
        } catch (e: Exception) {
            // Обрабатываем возможные сетевые ошибки
            Response.error<T>(-1, e.message?.toResponseBody() ?: "Network error".toResponseBody())
        }
    }
}
