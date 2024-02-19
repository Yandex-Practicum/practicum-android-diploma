package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Convertors
import ru.practicum.android.diploma.data.dto.request.VacancyDetailedRequest
import ru.practicum.android.diploma.data.dto.respone.VacancyDetailedResponse
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.model.DetailVacancy
import ru.practicum.android.diploma.util.Constant.SUCCESS_RESULT_CODE

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
