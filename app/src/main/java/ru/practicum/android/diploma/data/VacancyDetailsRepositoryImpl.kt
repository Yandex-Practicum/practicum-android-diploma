package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.converter.FavoriteVacancyDbConverter
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.data.network.VacancyDetailsRequest
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse
import ru.practicum.android.diploma.domain.models.VacancyDetail
import kotlin.collections.map

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDao: FavoriteVacancyDao,
    private val dbConverter: FavoriteVacancyDbConverter
) : VacancyDetailsRepository {
    override suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse {
        val response = networkClient.doRequest(
            VacancyDetailsRequest(id = id),
        )
        val data = response.data as? VacancyDetailDto
        return when {
            data == null -> GetVacancyDetailsResponse.Error
            response.resultCode != HTTP_OK -> {
                when (response.resultCode) {
                    HTTP_NOT_FOUND -> GetVacancyDetailsResponse.NotFound
                    HTTP_SERVER_ERROR -> GetVacancyDetailsResponse.ServerError
                    else -> GetVacancyDetailsResponse.Error
                }
            }
            else -> {
                val domainResult = data.toDomain()
                GetVacancyDetailsResponse.Success(domainResult)
            }
        }
    }

    override fun getVacancies(): Flow<List<VacancyDetail>> = flow {
        val vacancies = vacancyDao.observeFVacancies()
        emit(convertFromFavoriteVacanciesEntity(vacancies))
    }

    override fun getVacancyById(id: String): Flow<VacancyDetail> = flow {
        val trackEntity = vacancyDao.observeVacancyIsFavorite(id)
        trackEntity.let { dbConverter.map(it)}
    }

    override suspend fun addVacancyToFavorites(vacancyDetail: VacancyDetail) {
        vacancyDao.addVacancy(dbConverter.map(vacancyDetail))
    }

    override suspend fun deleteVacancyFromFavorites(id: String) {
        vacancyDao.deleteVacancy(id)
    }

    private companion object {
        const val HTTP_OK = 200

        const val HTTP_NOT_FOUND = 404

        const val HTTP_SERVER_ERROR = 500
    }

    private fun convertFromFavoriteVacanciesEntity(vacancies: List<FavoriteVacancyEntity>): List<VacancyDetail> {
        return vacancies.map { vacancy -> dbConverter.map(vacancy) }
    }
}
