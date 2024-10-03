package ru.practicum.android.diploma.vacancy.data.repositoryimpl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.vacancy.data.mappers.VacancyMappers
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailRepository

private const val TAG_VACANCY = "Vacancy"
class VacancyDetailRepositoryImpl(
    private val context: Context,
    private val networkClient: NetworkClient,
    private val database: AppDatabase
) : VacancyDetailRepository {

    override fun getVacancyNetwork(id: String): Flow<Resource<Vacancy>> =
        context.executeNetworkRequest<Response, Vacancy>(
            request = { networkClient.doRequest(HHApiVacancyRequest(id)) },
            successHandler = { response: Response ->
                Resource.Success(VacancyMappers.map(context, response as HHVacancyDetailResponse))
            }
        )

    override fun getVacancyDb(id: Int): Flow<Resource<Vacancy?>> = flow {
        runCatching {
            database.favoriteVacancyDao().getVacancy(id)
        }.fold(
            onSuccess = { vacancy ->
                emit(Resource.Success(VacancyMappers.map(vacancy)))
            },
            onFailure = { e ->
                Utils.outputStackTrace(TAG_VACANCY, e)
                emit(Resource.Error(e.message.toString()))
            }
        )
    }
}
