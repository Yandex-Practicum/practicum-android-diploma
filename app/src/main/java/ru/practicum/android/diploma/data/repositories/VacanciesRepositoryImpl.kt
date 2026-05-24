package ru.practicum.android.diploma.data.repositories

import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.mappers.toQueryMap
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancySearchRequest
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

class VacanciesRepositoryImpl(
    private val api: DiplomaApi,
    private val networkClient: NetworkClient,
) : VacanciesRepository {

    override suspend fun searchVacancies(request: VacancySearchRequest): Resource<VacancySearchResult> {
        return when (val result = networkClient.safeApiCall { api.searchVacancies(request.toQueryMap()) }) {
            is Resource.Success -> Resource.Success(result.data.toDomain())
            is Resource.Error -> Resource.Error(message = result.message, code = result.code)
            Resource.Loading -> Resource.Loading
        }
    }
}
