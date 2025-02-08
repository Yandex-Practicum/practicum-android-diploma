package ru.practicum.android.diploma.filter.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.common.data.dto.CountriesResponse
import ru.practicum.android.diploma.common.data.dto.CountryRequest
import ru.practicum.android.diploma.common.data.dto.IndustriesResponse
import ru.practicum.android.diploma.common.data.dto.IndustryRequest
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.common.data.dto.allregions.RegionsRequest
import ru.practicum.android.diploma.common.data.dto.allregions.RegionsResponse
import ru.practicum.android.diploma.common.data.dto.region.SearchRegionRequest
import ru.practicum.android.diploma.common.data.dto.region.SearchRegionResponse
import ru.practicum.android.diploma.common.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.common.util.RegionConverter
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.CountryViewState
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.domain.model.RegionViewState
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

class FilterRepositoryImpl(
    private val networkClient: RetrofitNetworkClient,
) : FilterRepository {
    override fun getIndustries(): Flow<IndustryViewState> = flow {
        val response = networkClient.doRequest(IndustryRequest)
        when (response.resultCode) {
            Response.SUCCESS_RESPONSE_CODE -> {
                val result = (response as IndustriesResponse).result
                if (result.isEmpty()) {
                    emit(IndustryViewState.NotFoundError)
                } else {
                    val data = mapIndustries(response)
                    emit(IndustryViewState.Success(data))
                }
            }

            Response.BAD_REQUEST_ERROR_CODE, Response.NOT_FOUND_ERROR_CODE -> {
                emit(IndustryViewState.NotFoundError)
            }

            Response.NO_INTERNET_ERROR_CODE -> {
                emit(IndustryViewState.ConnectionError)
            }

            else -> {
                emit(IndustryViewState.ServerError)
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getCountries(): Flow<CountryViewState> = flow {
        val response = networkClient.doRequest(CountryRequest)
        when (response.resultCode) {
            Response.SUCCESS_RESPONSE_CODE -> {
                val result = (response as CountriesResponse).result
                if (result.isEmpty()) {
                    emit(CountryViewState.NotFoundError)
                } else {
                    val data = mapCountries(response)
                    emit(CountryViewState.Success(data))
                }
            }

            Response.BAD_REQUEST_ERROR_CODE, Response.NOT_FOUND_ERROR_CODE -> {
                emit(CountryViewState.NotFoundError)
            }

            Response.NO_INTERNET_ERROR_CODE -> {
                emit(CountryViewState.ConnectionError)
            }

            else -> {
                emit(CountryViewState.ServerError)
            }
        }
    }

    override fun searchRegionsById(parentId: String): Flow<RegionViewState> = flow {
        val response = networkClient.doRequest(SearchRegionRequest(parentId))
        when (response.resultCode) {
            Response.SUCCESS_RESPONSE_CODE -> {
                val result = (response as SearchRegionResponse).areas
                if (result.isNullOrEmpty()) {
                    emit(RegionViewState.NotFoundError)
                } else {
                    val data = result.map { RegionConverter.convertToArea(it) }
                    Log.d("searchRegionsByIdRequest", "$data")
                    emit(RegionViewState.Success(data))
                }
            }

            Response.BAD_REQUEST_ERROR_CODE, Response.NOT_FOUND_ERROR_CODE -> {
                emit(RegionViewState.NotFoundError)
            }

            Response.NO_INTERNET_ERROR_CODE -> {
                emit(RegionViewState.ConnectionError)
            }

            else -> {
                emit(RegionViewState.ServerError)
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getParentRegionById(parentId: String): Flow<CountryViewState> = flow {
        val response = networkClient.doRequest(SearchRegionRequest(parentId))
        when (response.resultCode) {
            Response.SUCCESS_RESPONSE_CODE -> {
                val result = response as SearchRegionResponse
                Log.d("RegionName", "$result")
                if (result.name.isEmpty()) {
                    emit(CountryViewState.NotFoundError)
                } else {
                    val data = RegionConverter.applyCountryById(result)
                    Log.d("getParentRegionsByIdRequest", "$data")
                    emit(CountryViewState.CountrySelected(data))
                }
            }

            Response.BAD_REQUEST_ERROR_CODE, Response.NOT_FOUND_ERROR_CODE -> {
                emit(CountryViewState.NotFoundError)
            }

            Response.NO_INTERNET_ERROR_CODE -> {
                emit(CountryViewState.ConnectionError)
            }

            else -> {
                emit(CountryViewState.ServerError)
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllRegions(): Flow<RegionViewState> = flow {
        val response = networkClient.doRequest(RegionsRequest)
        when (response.resultCode) {
            Response.SUCCESS_RESPONSE_CODE -> {
                val result = (response as RegionsResponse).result
                if (result.isEmpty()) {
                    emit(RegionViewState.NotFoundError)
                } else {
                    val data = result.map { RegionConverter.convertToArea(it) }
                    emit(RegionViewState.Success(data))
                }
            }

            Response.BAD_REQUEST_ERROR_CODE, Response.NOT_FOUND_ERROR_CODE -> {
                emit(RegionViewState.NotFoundError)
            }

            Response.NO_INTERNET_ERROR_CODE -> {
                emit(RegionViewState.ConnectionError)
            }

            else -> {
                emit(RegionViewState.ServerError)
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun mapIndustries(response: IndustriesResponse): List<Industry> {
        return response.result.flatMap { industryDto ->
            industryDto.industries?.map { nestedIndustryDto ->
                Industry(id = nestedIndustryDto.id, name = nestedIndustryDto.name)
            } ?: emptyList()
        }.sortedBy { it.name }
    }

    private fun mapCountries(response: CountriesResponse): List<Country> {
        Log.d("MappingCheck", "Raw Country DTOs: ${response.result}")
        return response.result.flatMap { countryDto ->
            // Если поле countries != null, конвертируем, иначе пропускаем страну
            countryDto.countries?.map { nestedCountryDto ->
                Country(id = nestedCountryDto.id, name = nestedCountryDto.name)
            } ?: listOf(Country(id = countryDto.id, name = countryDto.name)) // Добавляем страну без поля countries
        }.sortedBy { it.name }
    }
}
