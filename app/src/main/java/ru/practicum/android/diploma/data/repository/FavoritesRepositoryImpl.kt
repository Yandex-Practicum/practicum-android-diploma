package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.database.converters.VacancyDetailDbConverter
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoritesRepositoryImpl(
    private val database: VacancyDao,
    private val converterVacancy: VacancyDetailDbConverter
) : FavoritesRepository {
    override fun setVacancy(vacancy: VacancyDetail): Flow<Boolean> {
        return flow {
            val vacancyEntity = converterVacancy.map(vacancy)
            val response = database.setFavoriteVacancy(vacancyEntity)
            if (response > -1) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    override fun getVacancy(id: String): Flow<VacancyDetail> {
        return flow {
            val vacancyEntity = database.getFavoritesVacancyById(id)
            val vacancy = converterVacancy.map(vacancyEntity)
            emit(vacancy)
        }
    }

    override fun getAllVacancies(): Flow<List<VacancyDetail>> {
        return flow {
            val listVacancyEntity = database.getAllFavoritesVacancies()
            emit(converterVacancy.map(listVacancyEntity))
        }
    }

    override fun checkVacanciesInFavorite(id: String): Flow<Boolean> {
        return flow {
            val response = database.checkInFavorite(id = id)
            emit(response)
        }
    }

    override fun deleteVacancyFromFavorite(id: String): Flow<Boolean> {
        return flow {
            val response = database.deleteVacancyFromFavorites(id)
            if (response == null || response == 0) {
                emit(false)
            } else {
                emit(true)
            }
        }
    }
}
