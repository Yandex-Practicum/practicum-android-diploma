package ru.practicum.android.diploma.data.dto.region.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.RegionsConverter
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.dto.request.AllRegionsRequest
import ru.practicum.android.diploma.data.dto.request.CountryRegionsRequest
import ru.practicum.android.diploma.data.dto.response.RegionResponse
import ru.practicum.android.diploma.domain.NetworkClient
import ru.practicum.android.diploma.domain.api.region.RegionsRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.Resource

class RegionsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val regionsConverter: RegionsConverter
) : RegionsRepository {

    override fun getRegions(areaId: String?): Flow<Resource<List<Region>>> = flow {
        if (areaId.isNullOrBlank()) {
            val response = networkClient.doRequest(AllRegionsRequest())
            when (response.code) {
                RetrofitNetworkClient.INTERNET_NOT_CONNECT -> {
                    emit(Resource.Error(message = VacancyError.NETWORK_ERROR))
                }
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    with(response as RegionResponse) {
                        val countriesData = regionsConverter.convertRegions(this)
                        val mergedList = ArrayList<Region>()
                        val filteredData = countriesData.filter { it.id != "1001" }
                        filteredData.forEach { country ->
                            val temporaryList = ArrayList<Region>()
                            temporaryList.addAll(country.includedRegions)
                            country.includedRegions.forEach { region ->
                                temporaryList.addAll(region.includedRegions)
                            }
                            temporaryList.map {
                                it.parentCountry = Country(
                                    id = country.id,
                                    name = country.name,
                                    parentId = country.parentId,
                                    includedRegions = ArrayList()
                                )
                            }
                            mergedList.addAll(temporaryList)
                        }
                        emit(Resource.Success(mergedList))
                    }
                }
                else -> {
                    emit(Resource.Error(message = VacancyError.SERVER_ERROR))
                }
            }
        } else {
            val response = networkClient.doRequest(CountryRegionsRequest(areaId))
            when (response.code) {
                RetrofitNetworkClient.INTERNET_NOT_CONNECT -> {
                    emit(Resource.Error(message = VacancyError.NETWORK_ERROR))
                }
                RetrofitNetworkClient.HTTP_OK_CODE -> {
                    with(response as RegionResponse) {
                        val parentCountry = regionsConverter.convertRegion(this)
                        val regionsData = regionsConverter.convertRegions(this)
                        val mergedList = ArrayList<Region>(regionsData)
                        regionsData.forEach {
                            mergedList.addAll(it.includedRegions)
                        }
                        mergedList.map {
                            it.parentCountry = Country(
                                id = parentCountry.id,
                                name = parentCountry.name,
                                parentId = parentCountry.parentId,
                                includedRegions = ArrayList()
                            )
                        }
                        emit(Resource.Success(mergedList))
                    }
                }
                else -> {
                    emit(Resource.Error(message = VacancyError.SERVER_ERROR))
                }
            }
        }
    }
}
