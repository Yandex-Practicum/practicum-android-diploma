package ru.practicum.android.diploma.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Resource
import ru.practicum.android.diploma.favorites.domain.entity.VacancyFavorite
import ru.practicum.android.diploma.search.domain.model.VacancyItems

interface FavoriteRepository {
    suspend fun getVacancyList(): Flow<Resource<List<VacancyItems>>>
    suspend fun insertFavoriteVacancy(vacancy: VacancyFavorite)
    suspend fun deleteVacancyById(id: String)
    fun getVacancyById(id: String): Flow<VacancyFavorite?>
    suspend fun isVacancyFavorite(vacancyId: String): Boolean
}
