package ru.practicum.android.diploma.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

interface FavoritesRepository {
    suspend fun add(details: VacancyDetails)
    suspend fun remove(id: String)
    fun getAll(): Flow<List<Vacancy>>
    fun getById(id: String): Flow<VacancyDetails?>
    fun isFavorite(id: String): Flow<Boolean>
}
