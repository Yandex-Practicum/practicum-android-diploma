package ru.practicum.android.diploma.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.AreasDto
import ru.practicum.android.diploma.data.dto.IndustriesResponseDto
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.util.ResponseCode
import ru.practicum.android.diploma.util.network.CheckNetworkConnect

const val HttpExceptionTag = "HttpException"

class RetrofitNetworkClient(
    private val api: HhApi,
    private val context: Context
) : NetworkClient {

    private suspend fun doRequest(actualRequest: suspend () -> Response): Response {
        // Если подключение к интернету отсутствует
        if (!CheckNetworkConnect.isNetworkAvailable(context)) {
            return Response().apply { resultCode = ResponseCode.NO_INTERNET.code }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = actualRequest()
                response.apply { resultCode = ResponseCode.SUCCESS.code }
            } catch (e: HttpException) {
                Log.w(HttpExceptionTag, e)
                Response().apply { resultCode = ResponseCode.SERVER_ERROR.code }
            }
        }
    }

    override suspend fun doRequestVacancies(text: String?, options: HashMap<String, Int>): Response {
        return doRequest { api.searchVacancies(text = text, options = options) }
    }

    override suspend fun doRequestVacancyDetails(vacancyId: String): Response {
        return doRequest { api.getVacancyDetails(vacancyId) }
    }

    override suspend fun doRequestArea(areaId: String): Response {
        return doRequest { api.getAreaInfo(areaId) }
    }

    override suspend fun doRequestAreas(): Response {
        return doRequest { AreasDto(api.getAreas()) }
    }

    override suspend fun doRequestIndustries(): Response {
        return doRequest { IndustriesResponseDto(industries = api.getIndustries()) }
    }
}
