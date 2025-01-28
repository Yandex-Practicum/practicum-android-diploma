package ru.practicum.android.diploma.domain.favorites.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {
    fun getFavorites(): Flow<Resource<List<Vacancy>>>

    suspend fun getFavoriteById(vacancyId: Long): Boolean
    suspend fun saveVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    suspend fun removeVacancyById(vacancyId: Long)
}
