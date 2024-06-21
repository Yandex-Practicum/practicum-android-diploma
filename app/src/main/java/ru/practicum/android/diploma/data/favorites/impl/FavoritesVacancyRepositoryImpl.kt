package ru.practicum.android.diploma.data.favorites.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.VacancyConverter
import ru.practicum.android.diploma.data.favorites.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import java.io.IOException

class FavoritesVacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConverter: VacancyConverter
) : FavoritesVacancyRepository {

    private val teg = "favorites"
    override suspend fun getAllFavoritesVacancy() = flow {
        emit(withContext(Dispatchers.IO) {
            try {
                appDatabase.favoriteVacancyDao().getFavorites().map { vacancy -> vacancyConverter.map(vacancy) }
            } catch (e: IOException) {
                Log.e(teg, "Caught exception:  ${e.message}")
                null
            }
        })
    }
    override suspend fun getOneFavoriteVacancy(vacancyId: String): DomainVacancy {
        val vacancyEntity = appDatabase.favoriteVacancyDao().getVacancyById(vacancyId)
        return vacancyConverter.map(vacancyEntity)
    }

    override suspend fun deleteFavoriteVacancy(vacancy: DomainVacancy): Int {
        val vacancyEntity = vacancyConverter.map(vacancy)
        appDatabase.favoriteVacancyDao().deleteFavoriteVacancy(vacancyEntity)
        val vacancyList = appDatabase.favoriteVacancyDao().getFavoriteIds()
        return vacancyList.size
    }

    override suspend fun insertFavoriteVacancy(vacancy: DomainVacancy) {
        val vacancyEntity = vacancyConverter.map(vacancy)
        appDatabase.favoriteVacancyDao().insertFavoriteVacancy(vacancyEntity)
    }

    override suspend fun getFavoriteIds(): List<String> {
        return appDatabase.favoriteVacancyDao().getFavoriteIds()
    }
}

