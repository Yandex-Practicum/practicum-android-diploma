package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface FavoritesRepository {
    fun setVacancy(vacancy: VacancyDetail): Flow<Boolean>
    fun getVacancy(id: String): Flow<VacancyDetail>
    fun getAllVacancies(): Flow<List<VacancyDetail>>
    fun checkVacanciesInFavorite(id: String): Flow<Boolean>
    fun deleteVacancyFromFavorite(id: String): Flow<Boolean>
}

