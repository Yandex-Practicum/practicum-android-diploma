package ru.practicum.android.diploma.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.core.data.network.dto.DetailVacancyResponse
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.api.DetailVacancyRepository

class DetailVacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : DetailVacancyRepository {
    override fun getDetailVacancyById(id: Long): Flow<Resource<DetailVacancy>> = flow {
        val response = networkClient.getDetailVacancyById(id)
        when (response.resultCode) {
            RetrofitNetworkClient.SUCCESSFUL_CODE -> {
                val detailVacancyResponse = response as DetailVacancyResponse
                val domainModel = VacancyMapper.mapToDomain(detailVacancyResponse)
                emit(Resource.Success(data = domainModel))
            }

            RetrofitNetworkClient.NETWORK_ERROR_CODE -> {
                emit(Resource.InternetError())
            }

            RetrofitNetworkClient.EXCEPTION_ERROR_CODE -> {
                emit(Resource.ServerError())
            }
        }
    }
}
