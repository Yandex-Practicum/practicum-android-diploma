package ru.practicum.android.diploma.search.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.filter.data.converter.countryDtoToCountry
import ru.practicum.android.diploma.filter.data.converter.mapRegionCodeResponseToRegionList
import ru.practicum.android.diploma.filter.data.model.Filter
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.search.data.network.AlternativeRemoteDataSource
import ru.practicum.android.diploma.search.data.network.CodeResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.data.network.dto.request.Request
import ru.practicum.android.diploma.search.data.network.dto.response.CountriesCodeResponse
import ru.practicum.android.diploma.search.data.network.dto.response.RegionCodeResponse
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.flatMap
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val converter: VacancyModelConverter,
    private val logger: Logger,
    private val context: Context,
    private val apiHelper: AlternativeRemoteDataSource
) : SearchRepository {

/*     @NewResponse
    @Suppress("UNCHECKED_CAST")
    override suspend fun searchVacancies(query: String): Either<Failure, Vacancies> {
        return ((apiHelper.doRequest(
            Request.VacanciesRequest(
                query,
                "0"
            )
         
        )) as Either<Failure, VacanciesResponse>).flatMap {
            if (it.found == 0) {
                logger.log(thisName, "searchVacancies: NOTHING FOUND")
                Either.Left(Failure.NotFound())
            } else {
                logger.log(thisName, "searchVacancies: FOUND = ${it.found}")
                Either.Right(converter.vacanciesResponseToVacancies(it))
            }
        }
    } */
    @Suppress("UNCHECKED_CAST")
    @NewResponse
    override suspend fun searchVacancies(query: String, page: String): Either<Failure, Vacancies> {
        return ((apiHelper.doRequest(
            Request.VacanciesRequest(
                query, page
            )
        )) as Either<Failure, VacanciesResponse>).flatMap {
            if (it.found == 0) {
                logger.log(thisName, "searchVacancies: NOTHING FOUND")
                Either.Left(Failure.NotFound())
            } else {
                logger.log(thisName, "searchVacancies: FOUND = ${it.found}")
                Either.Right(converter.vacanciesResponseToVacancies(it))
            }
        }
    }
    
    override suspend fun getCountries(): Flow<NetworkResponse<List<Country>>> = flow {
        logger.log(thisName, "getCountries(): Flow<NetworkResponse<List<Country>>>")
        val request = Filter.CountryRequest
        val response = networkClient.doRequest(request)
        emit(
            when (response.resultCode) {
                200 -> checkCountryData(response)
                -1 -> NetworkResponse.Offline(message = context.getString(R.string.error))
                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
            }
        )
    }

    override suspend fun getRegionsById(countryId: String): Flow<NetworkResponse<List<Region>>> = flow {
        logger.log(thisName, "getRegions($countryId: String): Flow<NetworkResponse<List<Region>>>")
        val request = Filter.RegionRequest(countryId)
        val response = networkClient.doRequest(request)
        emit(
            when (response.resultCode) {
                200  -> checkRegionData(response)
                -1   -> NetworkResponse.Offline(message = context.getString(R.string.error))
                else -> NetworkResponse.Error(message = context.getString(R.string.server_error))
            }
        )
    }


    private fun checkCountryData(response: CodeResponse): NetworkResponse<List<Country>> {
        val list = countryDtoToCountry((response as CountriesCodeResponse).results)
        return if (list.isEmpty())
            NetworkResponse.NoData(message = context.getString(R.string.empty_list))
        else
            NetworkResponse.Success(list).also {
                logger.log(thisName, "List<Country> = $list")
            }
    }

    private fun checkRegionData(response: CodeResponse): NetworkResponse<List<Region>> {
        logger.log(thisName, "checkRegionData(response: CodeResponse): NetworkResponse<List<Region>>")
        val list = mapRegionCodeResponseToRegionList(response as RegionCodeResponse)
        return if (list.isEmpty())
            NetworkResponse.NoData(message = context.getString(R.string.empty_list))
        else
            NetworkResponse.Success(list)
    }
}