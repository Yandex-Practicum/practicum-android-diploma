package ru.practicum.android.diploma.data.network

import com.google.gson.JsonParseException
import retrofit2.Response
import ru.practicum.android.diploma.util.Resource
import java.io.IOException

class NetworkClientImpl(
    private val connectionChecker: NetworkConnectionChecker
) : NetworkClient {

    override suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        if (!connectionChecker.isConnected()) {
            return Resource.Error(message = NO_INTERNET_CONNECTION_MESSAGE)
        }

        return try {
            apiCall().toResource()
        } catch (exception: IOException) {
            Resource.Error(message = exception.message)
        } catch (exception: JsonParseException) {
            Resource.Error(message = exception.message)
        }
    }

    private fun <T> Response<T>.toResource(): Resource<T> {
        val responseBody = body()

        return if (isSuccessful && responseBody != null) {
            Resource.Success(responseBody)
        } else {
            Resource.Error(
                message = message().takeIf { it.isNotBlank() },
                code = code()
            )
        }
    }

    companion object {
        private const val NO_INTERNET_CONNECTION_MESSAGE = "No internet connection"
    }
}
