package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoriteRepository {
    suspend fun addToFavorite(vacancy: Vacancy)
    suspend fun delFromFavorite(vacancy: Vacancy)
    fun getFavorites(): Flow<List<Vacancy>>
    fun getFavoriteById(vacId: String): Flow<Vacancy>
}
