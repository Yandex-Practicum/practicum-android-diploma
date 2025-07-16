package ru.practicum.android.diploma.favorites.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.FavoriteVacancyRepository
import ru.practicum.android.diploma.vacancy.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

class FavoriteVacancyRepositoryImpl(
    private val favoriteVacancyDao: FavoriteVacancyDao
) : FavoriteVacancyRepository {

    override suspend fun addToFavorites(vacancy: FavoriteVacancyEntity) {
        favoriteVacancyDao.insertFavorite(vacancy)
    }

    override suspend fun removeFromFavorites(vacancy: FavoriteVacancyEntity) {
        favoriteVacancyDao.deleteFavorite(vacancy)
    }

    override fun getAllFavorites(): Flow<List<FavoriteVacancyEntity>> {
        return favoriteVacancyDao.getAllFavorites()
    }

    override suspend fun isFavorite(id: String): Boolean {
        return favoriteVacancyDao.getFavoriteById(id) != null
    }

    override suspend fun removeFromFavorites(id: String) {
        favoriteVacancyDao.deleteFavoriteById(id)
    }
}
