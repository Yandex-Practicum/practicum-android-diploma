package ru.practicum.android.diploma.data.region

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.AreaConverter.mapToCountry
import ru.practicum.android.diploma.data.request.RegionByIdRequest
import ru.practicum.android.diploma.data.response.AreaDto
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.region.RegionRepository
import ru.practicum.android.diploma.util.Resource

class RegionRepositoryImpl(
    val networkClient: NetworkClient
) : RegionRepository {
    override fun searchRegion(regionId: String): Flow<Resource<Country>> = flow {
        val response = networkClient.doRequest(RegionByIdRequest(regionId))

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    val region = response as AreaDto
                    emit(Resource.Success(region.mapToCountry()))
                } catch (e: Throwable) {
                    emit(Resource.Error(response.resultCode.code))
                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}
