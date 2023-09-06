package ru.practicum.android.diploma.details.data.network

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.data.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val converter: VacancyModelConverter,
    private val logger: Logger,
    private val context: Context
) : RemoteDataSource {

    override suspend fun getVacancyFullInfo(id: String): Flow<NetworkResponse<VacancyFullInfo>> = flow {
        logger.log(thisName, "getVacancyFullInfo($id: String): Flow<NetworkResponse<VacancyFullInfo>>")
        val request = ru.practicum.android.diploma.search.data.network.Vacancy.FullInfoRequest(id)
        val response = (networkClient.doRequest(request) as? VacancyFullInfoModelDto)

        emit(
            when (response?.resultCode) {
                200 -> NetworkResponse.Success(converter.mapDetails(response))
                -1 -> NetworkResponse.Offline(message = context.getString(R.string.error))
                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
            }
        )
    }

    override suspend fun getSimilarVacancies(id: String): Flow<NetworkResponse<List<ru.practicum.android.diploma.search.domain.models.Vacancy>>> = flow {
        logger.log(thisName, "getSimilarVacancies($id: String): Flow<NetworkResponse<List<Vacancy>>>")
        val request = ru.practicum.android.diploma.search.data.network.Vacancy.SimilarVacanciesRequest(id)
        val response = networkClient.doRequest(request)

        emit(
            when (response.resultCode) {
                200 -> {
                    val resultList = (response as VacanciesSearchCodeResponse).items
                    if (resultList.isNullOrEmpty()) {
                        NetworkResponse.NoData(message = context.getString(R.string.no_data))
                    } else {
                        val vacancies = converter.mapList(resultList)
                        NetworkResponse.Success(data = vacancies)
                    }
                }
                -1 -> NetworkResponse.Offline(message = context.getString(R.string.error))
                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
            }
        )
    }
}