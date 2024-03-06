package ru.practicum.android.diploma.data.region

import android.graphics.HardwareBufferRenderer.RenderRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.converters.VacancyConverter.toVacancyDetail
import ru.practicum.android.diploma.data.request.RegionByIdRequest
import ru.practicum.android.diploma.data.response.AreaDto
import ru.practicum.android.diploma.data.response.AreasResponse
import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.data.vacancydetail.dto.DetailRequest
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.region.RegionRepository
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.industries.IndustryMapper

class RegionRepositoryImpl(
    val networkClient: NetworkClient
) : RegionRepository {
    override fun searchRegion(regionId: String): Flow<Resource<List<Country>>> = flow {
        val response = networkClient.doRequest(RegionByIdRequest(regionId))

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
//                try {
//                    val region = (response as AreaDto)
//                    emit(Resource.Success(region))
//                } catch (e: Throwable) {
//                    emit(Resource.Error(response.resultCode.code))
//                }
            }

            ResponseCodes.ERROR -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.NO_CONNECTION -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SERVER_ERROR -> emit(Resource.Error(response.resultCode.code))
        }
    }
}
