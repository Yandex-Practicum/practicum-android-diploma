package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.filters.IndustriesRequest
import ru.practicum.android.diploma.data.filters.IndustriesResponse
import ru.practicum.android.diploma.data.network.NetworkClientInterface
import ru.practicum.android.diploma.domain.filters.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.domain.models.IndustriesDetails
import ru.practicum.android.diploma.util.HTTP_200_OK
import ru.practicum.android.diploma.util.HTTP_500_INTERNAL_SERVER_ERROR
import ru.practicum.android.diploma.util.HTTP_NO_CONNECTION
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    private val network: NetworkClientInterface
) : IndustriesRepository {
    override fun getIndustries(): Flow<Resource<List<Industries>>> = flow {
        val response = network.doRequest(IndustriesRequest())
        when (response.resultCode) {
            HTTP_NO_CONNECTION -> emit(Resource.Error(HTTP_NO_CONNECTION))
            HTTP_200_OK -> {
                with(response as IndustriesResponse) {
                    val data = industries.map {
                        Industries(
                            id = it.id,
                            name = it.name,
                            industries = it.industries.map {
                                IndustriesDetails(
                                    id = it.id,
                                    name = it.name
                                )
                            }
                        )
                    }
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(HTTP_500_INTERNAL_SERVER_ERROR))
        }
    }
}
