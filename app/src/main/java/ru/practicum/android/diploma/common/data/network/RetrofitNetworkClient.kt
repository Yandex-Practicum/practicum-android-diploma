package ru.practicum.android.diploma.common.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.common.data.Mapper
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.common.data.dto.SearchVacancyRequest

class RetrofitNetworkClient(
    private val headHunterApi: HeadHunterApi,
    private val mapper: Mapper,
    // private val connectivityManager: ConnectivityManager
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        return if (dto is SearchVacancyRequest) {
            withContext(Dispatchers.IO) {
                try {
                    val resp = headHunterApi.searchVacancies(mapper.map(dto.expression))
                    resp.apply { resultCode = 200 }
                } catch (e: Throwable) {
                    Response().apply { resultCode = 500 }
                }
            }
        } else {
            Response().apply { resultCode = 400 }
        }
    }

}
