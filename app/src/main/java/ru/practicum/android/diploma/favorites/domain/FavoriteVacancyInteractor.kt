package ru.practicum.android.diploma.favorites.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

interface FavoriteVacancyInteractor {
    suspend fun add(vacancy: FavoriteVacancyEntity)
    suspend fun remove(vacancy: FavoriteVacancyEntity)
    suspend fun removeById(id: String)
    fun getAll(): Flow<List<FavoriteVacancyEntity>>
    suspend fun isFavorite(id: String): Boolean
}
