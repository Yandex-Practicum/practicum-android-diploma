package ru.practicum.android.diploma.data.vacancy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkCodes
import ru.practicum.android.diploma.data.search.VacancyDtoMapper
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetailsError
import ru.practicum.android.diploma.domain.models.VacancyDetailsSearchResult
import ru.practicum.android.diploma.util.isConnected

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDao: VacancyDao,
    private val context: android.content.Context
) : VacancyDetailsRepository {
    override suspend fun getVacancyDetails(id: String): VacancyDetailsSearchResult = withContext(Dispatchers.IO) {
        if (!isConnected(context)) {
            getVacancyFromDatabase(id)
        } else {
            loadVacancyFromNetwork(id)
        }
    }

    private suspend fun loadVacancyFromNetwork(id: String): VacancyDetailsSearchResult {
        val response: Response = networkClient.doRequest(VacancyDetailsRequest(id))

        return when (response.resultCode) {
            NetworkCodes.SUCCESS_CODE -> {
                val detailsResponce = response as VacancyDetailsResponse
                val vacancy = VacancyDtoMapper.map(detailsResponce.vacancy)
                VacancyDetailsSearchResult(vacancy, null)
            }

            NetworkCodes.NO_NETWORK_CODE -> {
                getVacancyFromDatabase(id)
            }

            NetworkCodes.NOT_FOUND_CODE -> {
                VacancyDetailsSearchResult(null, VacancyDetailsError.NotFound)
            }

            else -> {
                VacancyDetailsSearchResult(null, VacancyDetailsError.Server)
            }
        }
    }


    private suspend fun getVacancyFromDatabase(id: String): VacancyDetailsSearchResult {
        return try {
            val vacancyEntity = vacancyDao.getVacancyById(id)

            if (vacancyEntity != null) {
                val vacancy = vacancyEntity.toDomain()
                val vacancyWithoutLogo = vacancy.copy(logoUrl = null)
                VacancyDetailsSearchResult(vacancyWithoutLogo, null)
            } else {
                VacancyDetailsSearchResult(null, VacancyDetailsError.Network)
            }
        } catch (_: Exception) {
            VacancyDetailsSearchResult(null, VacancyDetailsError.Server)
        }
    }
}
