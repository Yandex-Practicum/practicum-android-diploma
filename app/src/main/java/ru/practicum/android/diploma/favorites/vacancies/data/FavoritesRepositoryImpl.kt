package ru.practicum.android.diploma.favorites.vacancies.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.favorites.vacancies.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesRepository

class FavoritesRepositoryImpl(
    private val dao: FavoriteVacancyDao
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<FavoriteVacancyEntity>> = flow {
        emit(dao.getAllFavorites())
    }

    override suspend fun addToFavorites(entity: FavoriteVacancyEntity) {
        dao.insertFavorite(entity)
    }

    override suspend fun removeFromFavorites(id: String) {
        dao.deleteFavoriteById(id)
    }

    override suspend fun isFavorite(id: String): Boolean =
        dao.isFavorite(id)
}
