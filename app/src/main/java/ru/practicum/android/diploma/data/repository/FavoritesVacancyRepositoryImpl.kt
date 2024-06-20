package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.VacancyConverter
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesVacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConverter: VacancyConverter
) : FavoritesVacancyRepository {
    override suspend fun getAllFavoritesVacancy() = flow {
        emit(withContext(Dispatchers.IO) {
            appDatabase.favoriteVacancyDao().getFavorites().map { vacancy -> vacancyConverter.map(vacancy) }
        })
    }

    override suspend fun getOneFavoriteVacancy(vacancyId: String): Vacancy {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteVacancy(vacancy: Vacancy): Int {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavoriteVacancy(vacancy: Vacancy) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteIds(): List<String> {
        TODO("Not yet implemented")
    }
}

