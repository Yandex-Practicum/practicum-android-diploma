package ru.practicum.android.diploma.search.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.data.model.Filter
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.Vacancy
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesSearchCodeResponse
import ru.practicum.android.diploma.search.data.network.dto.response.CountriesCodeResponse
import ru.practicum.android.diploma.search.data.network.dto.response.RegionCodeResponse
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.NetworkError
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val converter: VacancyModelConverter,
    private val logger: Logger,
    private val context: Context,
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

    override suspend fun getCountries(): Flow<NetworkResponse<List<Country>>> = flow {
        logger.log(thisName, "getCountries(): Flow<NetworkResponse<List<Country>>>")
        val request = Filter.CountryRequest
        val response = networkClient.doRequest(request)
        emit(
            when (response.resultCode) {
                200  -> checkCountryData(response)
                -1   -> NetworkResponse.Offline(message = context.getString(R.string.error))
                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
            }
        )
    }

    override suspend fun getRegions(query: String): Flow<NetworkResponse<List<Region>>> = flow {
        logger.log(thisName, "getRegions(): Flow<NetworkResponse<List<Region>>>")
        val request = Filter.RegionRequest(query)
        val response = networkClient.doRequest(request)
        emit(
            when (response.resultCode) {
                200  -> checkRegionData(response)
                -1   -> NetworkResponse.Offline(message = context.getString(R.string.error))
                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
            }
        )
    }

    override suspend fun getRegions(): Flow<NetworkResponse<List<Region>>> = flow {
//        logger.log(thisName, "getRegions(): Flow<NetworkResponse<List<Region>>>")
//        val request = Filter.RegionRequest()
//        val response = networkClient.doRequest(request)
//        emit(
//            when (response.resultCode) {
//                200  -> checkRegionData(response)
//                -1   -> NetworkResponse.Offline(message = context.getString(R.string.error))
//                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
//            }
//        )
    }

    private fun checkCountryData(response: CodeResponse): NetworkResponse<List<Country>> {
        val list = converter.countryDtoToCountry((response as CountriesCodeResponse).results)
        return if (list.isEmpty())
            NetworkResponse.NoData(message = context.getString(R.string.empty_list))
        else
            NetworkResponse.Success(list)
    }

    private fun checkRegionData(response: CodeResponse): NetworkResponse<List<Region>> {
        val list = (response as RegionCodeResponse).results.map {
            Region(name = it.name ?: "", area = it.area)
        }
        return if (list.isEmpty())
            NetworkResponse.NoData(message = context.getString(R.string.empty_list))
        else
            NetworkResponse.Success(list)
    }
}