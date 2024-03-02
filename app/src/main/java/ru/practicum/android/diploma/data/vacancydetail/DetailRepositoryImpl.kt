package ru.practicum.android.diploma.data.vacancydetail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancyDetail
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailRequest
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class DetailRepositoryImpl(
    private val networkClient: NetworkClient,
) : DetailRepository {

    override fun searchDetailInformation(vacancyId: String): Flow<Resource<VacancyDetail>> = flow {
        val response = networkClient.doRequest(DetailRequest(vacancyId))

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    val vacancy = (response as VacancyDetailDtoResponse).toVacancyDetail()
                    emit(Resource.Success(vacancy))
                } catch (e: Throwable) {
                    emit(Resource.Error(response.resultCode.code))
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}
