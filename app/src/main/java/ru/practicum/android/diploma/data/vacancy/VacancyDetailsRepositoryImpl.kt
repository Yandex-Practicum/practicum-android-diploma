package ru.practicum.android.diploma.data.vacancy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkCodes
import ru.practicum.android.diploma.data.search.VacancyDtoMapper
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetailsSearchResult

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyDetailsRepository {
    override suspend fun getVacancyDetails(id: String): VacancyDetailsSearchResult = withContext(Dispatchers.IO) {
        val response: Response = networkClient.doRequest(VacancyDetailsRequest(id))

        when (response.resultCode) {
            NetworkCodes.SUCCESS_CODE -> {
                val detailsResponce = response as VacancyDetailsResponse
                val vacancy = VacancyDtoMapper.map(detailsResponce.vacancy)
                VacancyDetailsSearchResult(vacancy, null)
            }

            NetworkCodes.NO_NETWORK_CODE -> {
                VacancyDetailsSearchResult(null, NetworkCodes.NO_NETWORK_CODE)
            }

            NetworkCodes.NOT_FOUND_CODE -> {
                VacancyDetailsSearchResult(null, NetworkCodes.NOT_FOUND_CODE)
            }

            NetworkCodes.SERVER_ERROR_CODE -> {
                VacancyDetailsSearchResult(null, NetworkCodes.NOT_FOUND_CODE)
            }

            else -> {
                VacancyDetailsSearchResult(null, response.resultCode)
            }
        }
    }
}
