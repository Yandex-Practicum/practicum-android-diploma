package ru.practicum.android.diploma.vacancy.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.vacancy.data.converter.VacancyDetailsNetworkConverter
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class GetVacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: VacancyDetailsNetworkConverter
) : GetVacancyDetailsRepository {

    override fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequest(VacancyDetailsRequest(vacancyId))
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK ->
                    Resource.Success(converter.map(response as VacancyDetailsResponse))

                else -> Resource.Error(response.resultCode)
            }
        )
    }
}
