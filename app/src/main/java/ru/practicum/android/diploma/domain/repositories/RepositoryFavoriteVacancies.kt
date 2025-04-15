package ru.practicum.android.diploma.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyShortDmEntity

interface RepositoryFavoriteVacancies {
    suspend fun insertVacancy(vacancy: VacancyShortDmEntity): Result<Unit>

    suspend fun getAllVacancies(): List<VacancyShortDmEntity>?

    suspend fun getById(vacancyId: Int): Result<VacancyShortDmEntity>

    suspend fun deleteById(vacancyId: Int): Result<Unit>

    fun getVacanciesFlow(): Flow<List<VacancyShortDmEntity>>

    suspend fun clearDatabase(): Result<Unit>
}
