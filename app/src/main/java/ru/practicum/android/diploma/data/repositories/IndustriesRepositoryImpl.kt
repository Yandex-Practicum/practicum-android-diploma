package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    private val api: DiplomaApi,
    private val networkClient: NetworkClient,
) : IndustriesRepository {

    override suspend fun getIndustries(): Resource<List<Industry>> {
        return withContext(Dispatchers.IO) {
            when (val result = networkClient.safeApiCall { api.getIndustries() }) {
                is Resource.Success -> Resource.Success(result.data.map { it.toDomain() })
                is Resource.Error -> Resource.Error(message = result.message, code = result.code)
                Resource.Loading -> Resource.Loading
            }
        }
    }
}
