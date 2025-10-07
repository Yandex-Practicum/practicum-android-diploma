package ru.practicum.android.diploma.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.database.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val database: VacancyDao,
    private val converterVacancy: VacancyDbConverter
) : FavoritesRepository {
    override fun setVacancy(vacancy: Vacancy): Flow<Boolean> {
        return flow {
            val vacancyEntity = converterVacancy.map(vacancy)
            val response = database.setFavoriteVacancy(vacancyEntity)
            Log.v("my", "response  $response")
            if (response > -1) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    override fun getVacancy(id: String): Flow<Vacancy> {
        return flow {
            val vacancyEntity = database.getFavoritesVacancyById(id)
            val vacancy = converterVacancy.map(vacancyEntity)
            emit(vacancy)
        }
    }

    override fun getAllVacancies(): Flow<List<Vacancy>> {
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
