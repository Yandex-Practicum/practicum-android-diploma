package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_BAD_REQUEST
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.search.data.dto.VacancyDto
import ru.practicum.android.diploma.search.data.dto.toVacancy
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse
import ru.practicum.android.diploma.search.domain.utils.Options
import ru.practicum.android.diploma.search.domain.utils.VacanciesData

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {
    override fun search(options: Options): Flow<VacanciesData<VacanciesResponse>> = flow {
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
                        VacanciesData.Data(vacanciesResponse)
                    }
                }

                else -> {
                    responseToError(response)
                }
            }
        )
    }

    private fun responseToError(response: Response): VacanciesData<VacanciesResponse> =
        VacanciesData.Error(
            when (response.resultCode) {
                RESULT_CODE_NO_INTERNET -> {
                    VacanciesData.VacanciesSearchError.NO_INTERNET
                }

                RESULT_CODE_BAD_REQUEST -> {
                    VacanciesData.VacanciesSearchError.CLIENT_ERROR
                }

                else -> {
                    VacanciesData.VacanciesSearchError.SERVER_ERROR
                }
            }
        )
}
