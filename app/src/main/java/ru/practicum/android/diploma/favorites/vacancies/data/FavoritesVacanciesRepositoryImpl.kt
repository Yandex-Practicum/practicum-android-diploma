package ru.practicum.android.diploma.favorites.vacancies.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.vacancies.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesRepository

class FavoritesVacanciesRepositoryImpl(
    private val dao: FavoriteVacancyDao
) : FavoritesVacanciesRepository {

    override fun getFavorites(): Flow<List<FavoriteVacancyEntity>> =
        dao.getAllFavorites()

    override suspend fun addToFavorites(entity: FavoriteVacancyEntity) {
        dao.insertFavorite(entity)
    }

    override suspend fun removeFromFavorites(id: String) {
        dao.deleteFavoriteById(id)
    }

    override suspend fun isFavorite(id: String): Boolean =
        dao.isFavorite(id)

    override suspend fun getFavoriteById(id: String): FavoriteVacancyEntity? {
        return dao.getFavoriteById(id)
    }
}
