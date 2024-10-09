package ru.practicum.android.diploma.vacancy.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.vacancy.data.converter.VacancyDetailsNetworkConverter
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class GetVacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: VacancyDetailsNetworkConverter,
    private val favoriteRepository: FavoriteVacancyRepository
) : GetVacancyDetailsRepository {

    override fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequest(VacancyDetailsRequest(vacancyId))
        emit(
            when (response.resultCode) {
                HttpStatusCode.OK -> {
                    val vacancy = converter.map(response as VacancyDetailsResponse)
                    favoriteRepository.updateVacancy(vacancy)
                    val existingVacancy = favoriteRepository.getVacancyByID(vacancyId).firstOrNull()
                    if (existingVacancy != null) vacancy.isFavorite = true
                    Resource.Success(vacancy)
                }
                HttpStatusCode.NOT_FOUND -> {
                    favoriteRepository.deleteVacancyById(vacancyId)
                    Resource.Error(HttpStatusCode.NOT_FOUND, null)
                }
                else -> {
                    val existingVacancy = favoriteRepository.getVacancyByID(vacancyId).firstOrNull()
                    if (existingVacancy != null) {
                        existingVacancy.isFavorite = true
                        Resource.Success(existingVacancy)
                    } else {
                        Resource.Error(HttpStatusCode.NOT_CONNECTED, null)
                    }
                }
            }
        )
    }
}
