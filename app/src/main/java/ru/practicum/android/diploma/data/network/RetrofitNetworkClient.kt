package ru.practicum.android.diploma.data.network

import android.content.Context
import okio.IOException
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.IndustryRequest
import ru.practicum.android.diploma.data.dto.IndustryResponse
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.NetworkCodes
import ru.practicum.android.diploma.util.isConnected
import java.net.SocketTimeoutException

class RetrofitNetworkClient(private val context: Context, private val service: VacancyApi) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected(context)) {
            return Response().apply {
                resultCode = NetworkCodes.NO_NETWORK_CODE
            }
        }
        return when (dto) {
            is IndustryRequest -> handleIndustriesRequest()
            else -> handleUnknownRequest()
        }
    }

    private suspend fun handleIndustriesRequest(): Response {
        return try {
            val industriesList = service.getIndustries()
            IndustryResponse(industriesList).apply {
                resultCode = NetworkCodes.SUCCESS_CODE
            }
        } catch (e: SocketTimeoutException) {
            createErrorResponse(NetworkCodes.TIMEOUT_CODE)
        } catch (e: IOException) {
            createErrorResponse(NetworkCodes.NO_NETWORK_CODE)
        } catch (e: HttpException) {
            createErrorResponse(e.code())
        }
    }

    private fun handleUnknownRequest(): Response {
        return createErrorResponse(NetworkCodes.BAD_REQUEST_CODE)
    }

    private fun createErrorResponse(code: Int): Response {
        return Response().apply {
            resultCode = code
        }
    }
}
