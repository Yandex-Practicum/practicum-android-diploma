package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesRepository {
    fun setVacancy(vacancy: Vacancy): Flow<Boolean>
    fun getVacancy(id: String): Flow<Vacancy>
    fun getAllVacancies(): Flow<List<Vacancy>>
    fun checkVacanciesInFavorite(id: String): Flow<Boolean>
    fun deleteVacancyFromFavorite(id: String): Flow<Boolean>
}

