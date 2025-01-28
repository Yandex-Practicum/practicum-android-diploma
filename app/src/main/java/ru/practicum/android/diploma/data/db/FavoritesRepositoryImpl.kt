package ru.practicum.android.diploma.data.db

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.favorites.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.ResponseCode
import java.io.IOException

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: VacanciesConverter
) : FavoritesRepository {

    override fun getFavorites(): Flow<Resource<List<Vacancy>>> = flow {
        try {
            appDatabase.vacancyDao().getAllFavorites().collect { entities ->
                if (entities.isEmpty()) {
                    emit(Resource.Success(emptyList()))
                } else {
                    val vacancies = convertFromDB(entities)
                    emit(Resource.Success(vacancies))
                }
            }
        } catch (e: IOException) {
            Log.i("DB", e.toString())
            emit(Resource.Error(ResponseCode.DATABASE_ERROR.code)) // e.message ?: String())
        }
    }

    override suspend fun getFavoriteById(vacancyId: Long): Flow<Boolean> = flow {
        val vacancyEntity = getFavouriteByIdRequest(vacancyId)
        emit(vacancyEntity != null)
    }

    private suspend fun getFavouriteByIdRequest(vacancyId: Long): VacancyEntity? {
        return withContext(Dispatchers.IO) {
            try {
                appDatabase.vacancyDao().getFavoriteById(vacancyId)
            } catch (e: IOException) {
                Log.i("DB", e.toString())
                null
            }
        }
    }

    override suspend fun saveVacancy(vacancy: Vacancy) {
        appDatabase.vacancyDao().addToFavorites(convertToDB(vacancy))
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        appDatabase.vacancyDao().removeFromFavorites(convertToDB(vacancy))
    }

    override suspend fun removeVacancyById(vacancyId: Long) {
        appDatabase.vacancyDao().removeById(vacancyId)
    }

    private fun convertFromDB(entity: List<VacancyEntity>) = entity.map(converter::convertFromDBtoVacancy)

    private fun convertToDB(vacancy: Vacancy) = converter.convertFromVacancyToDB(vacancy)
}
