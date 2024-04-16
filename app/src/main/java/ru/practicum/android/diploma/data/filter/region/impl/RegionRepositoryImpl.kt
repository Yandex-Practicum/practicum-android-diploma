package ru.practicum.android.diploma.data.filter.region.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import ru.practicum.android.diploma.data.converter.AreaConverter.mapToCountry
import ru.practicum.android.diploma.data.filter.country.dto.AreaDtoResponse
import ru.practicum.android.diploma.data.filter.region.RegionByIdRequest
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.region.RegionRepository
import ru.practicum.android.diploma.util.ResourceContentSearch

class RegionRepositoryImpl(
    private val networkClient: NetworkClient
) : RegionRepository {
    override fun searchRegion(regionId: String): Flow<ResourceContentSearch<Country>> = flow {
        val response = networkClient.doRequestFilter(RegionByIdRequest(regionId))

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    val region = response as AreaDtoResponse
                    emit(ResourceContentSearch.SuccessSearch(region.mapToCountry()))
                } catch (e: IOException) {
                    emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
                    throw e
                }
            }

            ResponseCodes.ERROR -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(ResourceContentSearch.ErrorSearch(response.resultCode.code))
        }
    }
}
