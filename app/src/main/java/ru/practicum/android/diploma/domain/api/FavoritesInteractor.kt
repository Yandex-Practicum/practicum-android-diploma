package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {
    suspend fun setVacancy(vacancy: Vacancy)
    fun getVacancy(id: Int): Flow<Vacancy>
    fun getAllVacancies(): Flow<List<Vacancy>>
    fun checkVacancyInFavorite(id: Int): Flow<Boolean>
    fun deleteVacancyFromFavorite(id: Int): Flow<Boolean>
}
