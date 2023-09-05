package ru.practicum.android.diploma.details.data.network

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.data.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.Vacancy
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
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
        val request = Vacancy.FullInfoRequest(id)
        val response = (networkClient.doRequest(request) as? VacancyFullInfoModelDto)

        emit(
            when (response?.resultCode) {
                200 -> NetworkResponse.Success(converter.mapDetails(response))
                -1 -> NetworkResponse.Offline(message = context.getString(R.string.error))
                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
            }
        )
    }
}