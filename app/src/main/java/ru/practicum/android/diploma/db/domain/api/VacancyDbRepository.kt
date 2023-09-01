package ru.practicum.android.diploma.db.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.data.entity.VacancyEntity

interface VacancyDbRepository {
    suspend fun insertVacancy(vacancyEntity: VacancyEntity)

    suspend fun deleteVacancy(vacancyEntity: VacancyEntity)

    suspend fun getFavouriteVacancy(): Flow<List<VacancyEntity>>
}