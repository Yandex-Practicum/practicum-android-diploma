package ru.practicum.android.diploma.data.favorites.impl

import android.database.sqlite.SQLiteException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.data.favorites.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteVacanciesRepositoryImpl(
    private val vacancyConverter: VacancyConverter,
    private val appDatabase: AppDatabase
) : FavoriteVacanciesRepository {

    override suspend fun insertFavoriteVacancy(vacancyForInsert: Vacancy) {
        appDatabase.favouritesVacancyDao().insertFavouritesVacancyEntity(vacancyConverter.map(vacancyForInsert))
    }

    override suspend fun deleteFavoriteVacancy(vacancyForDeleteId: String) {
        appDatabase.favouritesVacancyDao().deleteFavouritesVacancyEntity(vacancyForDeleteId)
    }

    override fun getFavoriteVacancies(): Flow<List<Vacancy>?> {
        return flow {
            try {
                val foundedFavoriteVacancies =
                    appDatabase.favouritesVacancyDao().getFavouritesVacancyList().map { item ->
                        vacancyConverter.map(item)
                    }
                emit(foundedFavoriteVacancies)
            } catch (e: SQLiteException) {
                Log.e("Error, GetVacancy", "An error occurred: ", e)
                emit(null)
            }
        }
    }

    override fun getFavoriteVacancyById(vacancyId: String): Flow<Vacancy?> {
        return flow {
            val foundedVacancy = appDatabase.favouritesVacancyDao().getFavoriteVacancyById(vacancyId)
            emit(
                if (foundedVacancy == null) {
                    null
                } else {
                    vacancyConverter.map(foundedVacancy)
                }
            )
        }
    }

}
