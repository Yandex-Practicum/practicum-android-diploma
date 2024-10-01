package ru.practicum.android.diploma.vacancy.data.repositoryimpl

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.data.networkclient.api.NetworkClient
import ru.practicum.android.diploma.data.networkclient.api.dto.HHApiVacancyRequest
import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacancyDetailResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.HttpStatus
import ru.practicum.android.diploma.vacancy.R
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailRepository
import ru.practicum.android.diploma.vacancy.util.VacancyConverter

class VacancyDetailRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyConverter: VacancyConverter,
    private val context: Context,
) : VacancyDetailRepository {

    override fun listVacancy(id: String): Flow<Resource<VacancyDetail>> = flow {
        val response = networkClient.doRequest(HHApiVacancyRequest(id))
        when (response.resultCode) {
            HttpStatus.NO_INTERNET -> {
                emit(Resource.Error(context.getString(R.string.check_network_connection)))
            }

            HttpStatus.OK -> {
                with(response as HHVacancyDetailResponse) {
                    emit(Resource.Success(vacancyConverter.map(response)))
                }
            }

            HttpStatus.CLIENT_ERROR -> {
                emit(Resource.Error(context.getString(R.string.request_was_not_accepted, response.resultCode)))
            }

            HttpStatus.SERVER_ERROR -> {
                emit(Resource.Error(context.getString(R.string.unexpcted_error, response.resultCode)))
            }
        }
    }

    companion object {
        private const val TAG = "VacanciyDetailRepositoryImpl"
    }
}
