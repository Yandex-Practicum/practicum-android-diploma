package ru.practicum.android.diploma.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.data.Filter
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.Vacancy
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse
import ru.practicum.android.diploma.search.data.network.dto.response.CountriesCodeResponse
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.NetworkError
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val converter: VacancyModelConverter,
    private val logger: Logger,
) : SearchRepository {

    override suspend fun search(query: String): Flow<FetchResult> {
        logger.log(thisName, "fun search($query: String): Flow<FetchResult>")

        val request = Vacancy.SearchRequest(query)
        val response = networkClient.doRequest(request)

        return when (response.resultCode) {
            in 100..399 -> {
                val resultList = (response as VacanciesSearchCodeResponse).items
                if (resultList.isNullOrEmpty()) {
                    flowOf(FetchResult.Error(NetworkError.SEARCH_ERROR))
                } else {
                    val vacancies = converter.mapList(resultList)
                    val count = response.found ?: 0
                    return flowOf(FetchResult.Success(data = vacancies, count = count))
                }
            }

            in 400..499 -> {
                flowOf(FetchResult.Error(NetworkError.SEARCH_ERROR))
            }

            else -> {
                flowOf(FetchResult.Error(NetworkError.CONNECTION_ERROR))
            }
        }
    }

    override suspend fun getCountries(): Flow<List<Country>> {
        logger.log(thisName, "getCountries(): Flow<List<Country>>")

        val request = Filter.CountryRequest
        val response = networkClient.doRequest(request)
        return if (response.resultCode == 200) {
            flowOf((response as CountriesCodeResponse).results.map {
                Country(
                    url = it.url,
                    id = it.id,
                    name = it.name
                )
            })
        } else flowOf(emptyList())
    }
}