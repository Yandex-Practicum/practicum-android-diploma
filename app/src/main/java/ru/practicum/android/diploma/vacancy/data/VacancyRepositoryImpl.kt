package ru.practicum.android.diploma.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl.Companion.NOT_FOUND
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl.Companion.OK_RESPONSE
import ru.practicum.android.diploma.util.InternetConnectionChecker
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.data.db.FavoriteVacancyMapper
import ru.practicum.android.diploma.vacancy.data.db.FavoriteVacancyMapper.mapDtoToDomain
import ru.practicum.android.diploma.vacancy.data.db.FavoriteVacancyMapper.mapDtoToEntity
import ru.practicum.android.diploma.vacancy.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val favoriteVacancyDao: FavoriteVacancyDao,
    private val internetConnectionChecker: InternetConnectionChecker
) : VacancyRepository {

    override fun getVacancyDetails(id: String): Flow<Resource<VacancyDetails>> = flow {
        if (!internetConnectionChecker.isInternetAvailable()) {
            val vacancyFromDb = favoriteVacancyDao.getFavoriteById(id)
            if (vacancyFromDb != null) {
                emit(Resource.Success(FavoriteVacancyMapper.map(vacancyFromDb)))
            } else {
                emit(Resource.Error("no_internet"))
            }
        } else {
            when (val response = networkClient.getVacancyDetails(id)) {
                is VacancyDetailsResponse -> {
                    if (response.resultCode == OK_RESPONSE) {
                        val dto = response.vacancy

                        if (favoriteVacancyDao.getFavoriteById(id) != null) {
                            favoriteVacancyDao.insertFavorite(mapDtoToEntity(dto))
                        }
                        emit(Resource.Success(mapDtoToDomain(dto)))

                    } else if (response.resultCode == NOT_FOUND) {
                        favoriteVacancyDao.deleteFavoriteById(id)
                        emit(Resource.Error("not_found"))
                    } else {
                        emit(Resource.Error("server_error"))
                    }
                }

                else -> {
                    emit(Resource.Error("server_error"))
                }
            }
        }
    }
}
