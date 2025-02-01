package ru.practicum.android.diploma.data.industries

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.IndustriesConverter
import ru.practicum.android.diploma.data.dto.IndustriesResponseDto
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.industries.api.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.ResponseCode

class IndustriesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val industriesConverter: IndustriesConverter,
) : IndustriesRepository {
    override suspend fun getIndustriesList(): Resource<List<Industry>> {
        val response = networkClient.doRequestIndustries()
        return when (response.resultCode) {
            ResponseCode.SUCCESS.code -> {
                with(response as IndustriesResponseDto) {
                    Resource.Success(industriesConverter.convertIndustriesResponse(response))
                }
            }

            else -> Resource.Error(response.resultCode)
        }
    }
}
