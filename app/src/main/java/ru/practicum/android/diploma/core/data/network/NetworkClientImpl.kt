package ru.practicum.android.diploma.core.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import ru.practicum.android.diploma.core.domain.repository.ConnectivityRepository
import java.io.IOException

class NetworkClientImpl(
    private val retrofit: Retrofit,
    private val connectivity: ConnectivityRepository,
) : NetworkClient {

    override suspend fun doRequest(request: Request): Response = withContext(Dispatchers.IO) {
        if (!connectivity.isConnected()) {
            Response().apply { resultCode = ResultCode.NO_INTERNET }
        } else {
            try {
                executeRequest(request)
            } catch (e: HttpException) {
                Response().apply { resultCode = e.code() }
            } catch (_: IOException) {
                Response().apply { resultCode = ResultCode.SERVER_ERROR }
            }
        }
    }

    @Suppress("UnusedParameter", "UnusedPrivateMember")
    private fun executeRequest(request: Request): Response {
        //  is SearchRequest -> retrofit.create(SearchApi::class.java).search(...)
        return Response().apply { resultCode = ResultCode.CLIENT_ERROR }
    }
}
