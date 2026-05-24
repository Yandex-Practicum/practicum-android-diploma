package ru.practicum.android.diploma.core.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import ru.practicum.android.diploma.core.domain.repository.ConnectivityRepository
import ru.practicum.android.diploma.search.data.network.HhSearchApi
import ru.practicum.android.diploma.vacancy.data.network.VacancyApi
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
    private suspend fun executeRequest(request: Request): Response {
        return when (request) {
            is Request.SearchRequest -> {
                val api = retrofit.create(HhSearchApi::class.java)
                val response = api.searchVacancies(request.params)
                Response().apply {
                    resultCode = ResultCode.SUCCESS
                    data = response
                }
            }
            is Request.VacancyDetailsRequest -> {
                val api = retrofit.create(VacancyApi::class.java)
                val response = api.getVacancyById(request.id)
                Response().apply {
                    resultCode = ResultCode.SUCCESS
                    data = response
                }
            }
        }
    }
}
