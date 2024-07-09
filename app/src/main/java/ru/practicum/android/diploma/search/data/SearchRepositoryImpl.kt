package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_NO_INTERNET
import ru.practicum.android.diploma.search.data.dto.RESULT_CODE_SUCCESS
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.dto.SearchResponse
import ru.practicum.android.diploma.search.data.dto.toVacancy
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {
    override fun search(options: SearchRequest): Flow<VacanciesResponse> = flow {
        val response = networkClient.doRequest(options)

        when (response.resultCode) {
            RESULT_CODE_SUCCESS -> {
                with(response as SearchResponse) {
                    val vacanciesList = items.map {
                        it.toVacancy()
                    }
                    emit(VacanciesResponse(vacanciesList, found, RESULT_CODE_SUCCESS))
                }
            }

            else -> {
                emit(VacanciesResponse(emptyList(), 0, RESULT_CODE_NO_INTERNET))
            }
        }
    }
}
