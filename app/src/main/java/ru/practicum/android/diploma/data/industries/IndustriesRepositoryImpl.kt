package ru.practicum.android.diploma.data.industries

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.ResponseCodes
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.domain.industries.IndustriesRepository
import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.industries.IndustryMapper

class IndustriesRepositoryImpl(
    val networkClient: NetworkClient
) : IndustriesRepository {
    override fun searchIndustries(): Flow<Resource<List<ParentIndustriesAllDeal>>> = flow {
        val response = networkClient.doRequest(IndustriesRequest)

        when (response.resultCode) {
            ResponseCodes.DEFAULT -> emit(Resource.Error(response.resultCode.code))
            ResponseCodes.SUCCESS -> {
                try {
                    emit(Resource.Success(
                        IndustryMapper.mapToEntityList(
                            (response as IndustriesResponse).industries
                        ).sortedBy { industries ->
                            industries.name
                        }
                    ))
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
