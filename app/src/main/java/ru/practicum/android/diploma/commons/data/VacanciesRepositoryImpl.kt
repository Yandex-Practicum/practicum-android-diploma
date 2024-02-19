package ru.practicum.android.diploma.commons.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.commons.data.Constant.SUCCESS_RESULT_CODE
import ru.practicum.android.diploma.commons.data.dto.detailed.VacancyDetailedRequest
import ru.practicum.android.diploma.commons.data.dto.detailed.VacancyDetailedResponse
import ru.practicum.android.diploma.commons.data.network.NetworkClient
import ru.practicum.android.diploma.commons.domain.api.VacanciesRepository
import ru.practicum.android.diploma.commons.domain.model.DetailVacancy

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {

    override fun getDetailVacancy(
        id: String
    ): Flow<Pair<DetailVacancy?, Int>> = flow {
        val response = networkClient.doRequest(VacancyDetailedRequest(id))
        if (response.responseCode == SUCCESS_RESULT_CODE) {
            val information = (response as VacancyDetailedResponse).information
            emit(
                Pair(
                    Convertors().convertorToDetailVacancy(information),
                    response.responseCode
                )
            )
        } else {
            emit(Pair(null, response.responseCode))
        }
    }
}
