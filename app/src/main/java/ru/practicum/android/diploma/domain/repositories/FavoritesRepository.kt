package ru.practicum.android.diploma.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoritesRepository {
    suspend fun insertVacancy(vacancy: VacancyEntity)
    suspend fun deleteVacancy(id: String)
    fun getAllVacancies(): Flow<List<VacanciesPreview>>
    suspend fun getVacancy(id: String): VacancyDetails
    suspend fun isFavorite(id: String): Boolean
}
