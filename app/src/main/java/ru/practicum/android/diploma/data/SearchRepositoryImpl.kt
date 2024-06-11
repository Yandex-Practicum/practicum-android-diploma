package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.converters.VacancyDtoConverter
import ru.practicum.android.diploma.data.dto.SearchRequest
import ru.practicum.android.diploma.data.dto.SearchResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.VacancyPage

class SearchRepositoryImpl(
    private val client: NetworkClient,
    private val converter: VacancyDtoConverter
) : SearchRepository {

    override fun searchRequest(options: Map<String, String>): Flow<Result<VacancyPage>> = flow {
        val response = client.doRequest(SearchRequest(options))
        when (response.resultCode) {
            CLIENT_SUCCESS_RESULT_CODE -> {
                val searchResponse = response as SearchResponse
                val list = searchResponse.items
                val page = searchResponse.page
                val pages = searchResponse.pages
                val found = searchResponse.found
                if (!list.isNullOrEmpty()) {
                    emit(
                        Result.success(VacancyPage(list.map { converter.map(it) }, page, pages, found))
                    )
                } else {
                    emit(
                        Result.success(VacancyPage(listOf(), page, pages, found))
                    )
                }
            }

            else -> {
                emit(
                    Result.failure(Throwable(response.resultCode.toString()))
                )
            }
        }
    }

    companion object {
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
