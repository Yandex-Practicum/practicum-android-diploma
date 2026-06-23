package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {
    fun getFavoriteVacancies(): Flow<List<Vacancy>>
    suspend fun getVacancy(vacancyId: String): Vacancy?
    suspend fun addVacancy(vacancy: Vacancy)
    suspend fun removeVacancy(vacancyId: String)
    suspend fun isInFavorites(vacancyId: String): Boolean
}
