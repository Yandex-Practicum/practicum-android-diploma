package ru.practicum.android.diploma.vacancy.data.repositoryimpl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.vacancy.data.mappers.VacancyNetworkMapper
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailRepository

class VacancyDetailRepositoryImpl(
    private val context: Context,
    private val networkClient: NetworkClient,
) : VacancyDetailRepository {

    override fun listVacancy(id: String): Flow<Resource<Vacancy>> =
        context.executeNetworkRequest<Response, Vacancy>(
            request = { networkClient.doRequest(HHApiVacancyRequest(id)) },
            successHandler = { response: Response ->
                Resource.Success(VacancyNetworkMapper.map(context, response as HHVacancyDetailResponse))
            }
        )
}
