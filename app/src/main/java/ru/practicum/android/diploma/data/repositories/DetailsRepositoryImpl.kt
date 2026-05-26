package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequestDto
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.DiplomaApi
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.DetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class DetailsRepositoryImpl(
    private val api: DiplomaApi,
    private val networkClient: NetworkClient,
) : DetailsRepository {

    override suspend fun getVacancyDetails(id: String): Resource<VacancyDetail> = withContext(Dispatchers.IO) {
        val requestDto = VacancyDetailsRequestDto(id)
        when (val result = networkClient.safeApiCall { api.getVacancyDetails(requestDto.id) }) {
            is Resource.Success -> Resource.Success(result.data.toDomain())
            is Resource.Error -> Resource.Error(message = result.message, code = result.code)
            Resource.Loading -> Resource.Loading
        }
    }

}
