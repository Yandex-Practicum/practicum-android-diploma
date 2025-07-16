package ru.practicum.android.diploma.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.mock.MockData
import ru.practicum.android.diploma.vacancy.data.db.FavoriteVacancyMapper
import ru.practicum.android.diploma.vacancy.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetails
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val favoriteVacancyDao: FavoriteVacancyDao
) : VacancyRepository {

    override fun getVacancyDetails(id: String): Flow<Resource<VacancyDetails>> = flow {
        kotlinx.coroutines.delay(MOCK_NETWORK_DELAY_MILLIS)

        val mockVacancy = MockData.getVacancyById(id)

        if (mockVacancy != null) {
            val dbVacancy = favoriteVacancyDao.getFavoriteById(id)
            if (dbVacancy != null) {
                favoriteVacancyDao.insertFavorite(mockVacancy)
            }

            emit(Resource.Success(FavoriteVacancyMapper.map(mockVacancy)))

        } else {
            val dbVacancy = favoriteVacancyDao.getFavoriteById(id)

            if (dbVacancy != null) {
                favoriteVacancyDao.deleteFavorite(dbVacancy)
                emit(Resource.Error("not_found"))
            } else {
                emit(Resource.Error("not_found"))
            }
        }
    }

    companion object {
        private const val MOCK_NETWORK_DELAY_MILLIS = 1000L
    }
}
