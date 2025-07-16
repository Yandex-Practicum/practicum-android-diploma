package ru.practicum.android.diploma.favorites.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

class FavoriteVacancyInteractor(
    private val repository: FavoriteVacancyRepository
) {
    suspend fun add(vacancy: FavoriteVacancyEntity) = repository.addToFavorites(vacancy)

    suspend fun remove(vacancy: FavoriteVacancyEntity) = repository.removeFromFavorites(vacancy)

    fun getAll(): Flow<List<FavoriteVacancyEntity>> = repository.getAllFavorites()

    suspend fun isFavorite(id: String): Boolean = repository.isFavorite(id)

    suspend fun remove(id: String) = repository.removeFromFavorites(id)
}
