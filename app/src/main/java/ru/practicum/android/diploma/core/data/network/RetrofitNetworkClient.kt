package ru.practicum.android.diploma.core.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.network.dto.Response
import ru.practicum.android.diploma.core.domain.model.SearchFilterParameters
import java.io.IOException
import java.net.SocketTimeoutException

class RetrofitNetworkClient(
    private val hhApi: HhApi,
    private val connectionChecker: ConnectionChecker,
) : NetworkClient {

    override suspend fun getVacanciesByPage(
        searchText: String,
        filterParameters: SearchFilterParameters,
        page: Int,
        perPage: Int
    ): Response {
        if (!connectionChecker.isConnected()) {
            return Response().apply { resultCode = NETWORK_ERROR_CODE }
        }
        return withContext(Dispatchers.IO) {
            executeRequestGetVacanciesByPage(searchText, filterParameters, page, perPage, hhApi)
        }
    }

    private suspend fun executeRequestGetVacanciesByPage(
        searchText: String,
        filterParameters: SearchFilterParameters,
        page: Int,
        perPage: Int,
        hhApi: HhApi
    ): Response {
        return try {
            val queryMap = mutableMapOf(
                HhApiQuery.PAGE.value to page.toString(),
                HhApiQuery.PER_PAGE.value to perPage.toString(),
                HhApiQuery.SEARCH_TEXT.value to searchText
            )
            queryMap.putAll(getQueryFilterMap(filterParameters))
            val response = hhApi.getVacancies(queryMap)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                body.apply { resultCode = SUCCESSFUL_CODE }
            } else {
                Response().apply { resultCode = response.code() }
            }
        } catch (e: SocketTimeoutException) {
            Log.e(RetrofitNetworkClient::class.java.simpleName, e.stackTraceToString())
            Response().apply { resultCode = NETWORK_ERROR_CODE }
        } catch (e: IOException) {
            Log.e(RetrofitNetworkClient::class.java.simpleName, e.stackTraceToString())
            Response().apply { resultCode = EXCEPTION_ERROR_CODE }
        }
    }

    private fun getQueryFilterMap(filterParameters: SearchFilterParameters): Map<String, String> {
        val filterMap = mutableMapOf<String, String>()
        if (filterParameters.salary.isNotEmpty()) {
            filterMap[HhApiQuery.SALARY_FILTER.value] = filterParameters.salary
        }
        if (filterParameters.industriesId.isNotEmpty()) {
            filterMap[HhApiQuery.INDUSTRY_FILTER.value] = filterParameters.industriesId
        }
        if (filterParameters.regionId.isNotEmpty()) {
            filterMap[HhApiQuery.REGION_FILTER.value] = filterParameters.regionId
        }
        if (filterParameters.isOnlyWithSalary) {
            filterMap[HhApiQuery.IS_ONLY_WITH_SALARY_FILTER.value] = "true"
        }
        return filterMap
    }

    override suspend fun getDetailVacancyById(id: Long): Response {
        if (!connectionChecker.isConnected()) {
            return Response().apply { resultCode = NETWORK_ERROR_CODE }
        }
        return withContext(Dispatchers.IO) {
            executeRequestGetDetailVacancyById(id, hhApi)
        }
    }

    private suspend fun executeRequestGetDetailVacancyById(id: Long, hhApi: HhApi): Response {
        return try {
            val response = hhApi.getVacancy(id)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                body.apply { resultCode = SUCCESSFUL_CODE }
            } else {
                Response().apply { resultCode = response.code() }
            }
        } catch (e: SocketTimeoutException) {
            Log.e(RetrofitNetworkClient::class.java.simpleName, e.stackTraceToString())
            Response().apply { resultCode = NETWORK_ERROR_CODE }
        } catch (e: IOException) {
            Log.e(RetrofitNetworkClient::class.java.simpleName, e.stackTraceToString())
            Response().apply { resultCode = EXCEPTION_ERROR_CODE }
        }
    }

    companion object {
        const val SUCCESSFUL_CODE = 200
        const val EXCEPTION_ERROR_CODE = -2
        const val NETWORK_ERROR_CODE = -1
    }
}
