package ru.practicum.android.diploma.search.data.network

import android.Manifest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.search.utils.NetworkConnectionChecker
import java.io.IOException

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
suspend fun <T> safeApiCall(
    networkConnectionChecker: NetworkConnectionChecker,
    apiCall: suspend () -> T
): Resource<T> {

    if (!networkConnectionChecker.isNetworkAvailable()) {
        return Resource.Error(
            message = "Нет подключения к интернету"
        )
    }

    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            Resource.Success(response)
        } catch (e: HttpException) {
            when (e.code()) {
                403 -> Resource.Error("403 Forbidden", exception = e)
                404 -> Resource.Error("404 Not Found", exception = e)
                else -> {
                    Resource.Error("Ошибка сервера", exception = e)
                }
            }
        } catch (e: IOException) {
            Resource.Error("Плохое соединение", exception = e)
        } catch (e: Exception) {
            Resource.Error("Неизвестная ошибка", exception = e)
        }
    }
}
