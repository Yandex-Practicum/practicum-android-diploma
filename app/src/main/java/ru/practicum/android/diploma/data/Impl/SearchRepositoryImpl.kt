package ru.practicum.android.diploma.data.Impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.data.dto.Responce
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.toVacancy
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.util.Options
import ru.practicum.android.diploma.util.ResponseData

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {

    override fun search(options: Options): Flow<ResponseData<VacanciesResponse>> = flow {
        emit(
            when (val response = networkClient.doRequest(SearchRequest(Options.toMap(options)))) {
                is SearchResponse -> {
                    with(response) {
                        val vacanciesResponse = VacanciesResponse(
                            items.map(VacancyDto::toVacancy),
                            found,
                            page,
                            pages,
                        )
                        ResponseData.Data(vacanciesResponse)
                    }
                }

                else -> {
                    responseToError(response)
                }
            }
        )
    }

    private fun responseToError(response: Responce): ResponseData<VacanciesResponse> =
        ResponseData.Error(
            when (response.resultCode) {
                RESULT_CODE_NO_INTERNET -> {
                    ResponseData.ResponseError.NO_INTERNET
                }

                RESULT_CODE_BAD_REQUEST -> {
                    ResponseData.ResponseError.CLIENT_ERROR
                }

                else -> {
                    ResponseData.ResponseError.SERVER_ERROR
                }
            }
        )
}
