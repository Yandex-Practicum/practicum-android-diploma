package ru.practicum.android.diploma.vacancy.data.repositoryimpl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.network.Response
import ru.practicum.android.diploma.commonutils.network.executeNetworkRequest
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.request.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse
import ru.practicum.android.diploma.vacancy.data.mappers.VacancyMappers
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailRepository

private const val TAG_VACANCY = "Vacancy"
private const val TAG_CHECK_VACANCY_EXIST = "CheckVacancyExists"
private const val TAG_VACANCY_DELETE = "VacancyDelete"

internal class VacancyDetailRepositoryImpl(
    private val context: Context,
    private val networkClient: NetworkClient,
    private val database: AppDatabase,
    private val externalNavigator: ExternalNavigator
) : VacancyDetailRepository {

    override fun getVacancyNetwork(id: String): Flow<Resource<Vacancy>> =
        context.executeNetworkRequest(
            request = { networkClient.doRequest(HHApiVacancyRequest(id)) },
            successHandler = { response: Response ->
                Resource.Success(VacancyMappers.map(context, response as HHVacancyDetailResponse))
            }
        )

    override fun getVacancyDb(id: Int): Flow<Resource<Vacancy?>> = Utils.executeQueryDb(
        query = { database.favoriteVacancyDao().getVacancy(id).let { VacancyMappers.map(it) } },
        tag = TAG_VACANCY
    )

    override fun checkVacancyExists(id: Int): Flow<Resource<Int>> = Utils.executeQueryDb(
        query = { database.favoriteVacancyDao().checkVacancyExists(id) },
        tag = TAG_CHECK_VACANCY_EXIST
    )

    override fun addVacancy(vacancy: Vacancy): Flow<Resource<Long>> = Utils.executeQueryDb(
        query = { database.favoriteVacancyDao().insertOrUpdateVacancy(VacancyMappers.map(vacancy)) },
        tag = TAG_VACANCY_DELETE
    )

    override fun deleteVacancy(id: Int): Flow<Resource<Int>> = Utils.executeQueryDb(
        query = { database.favoriteVacancyDao().deleteVacancy(id) },
        tag = TAG_VACANCY_DELETE
    )

    override fun share(shareLink: String) {
        externalNavigator.shareLink(shareLink)
    }
}
