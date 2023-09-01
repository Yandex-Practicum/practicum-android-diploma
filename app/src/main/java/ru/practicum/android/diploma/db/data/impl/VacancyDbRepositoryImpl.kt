package ru.practicum.android.diploma.db.data.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.AppDataBase
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.db.domain.api.VacancyDbRepository

class VacancyDbRepositoryImpl(private val appDataBase: AppDataBase): VacancyDbRepository {
    override suspend fun insertVacancy(vacancyEntity: VacancyEntity) {
        appDataBase.vacancyDao().insertVacancy(vacancyEntity)
    }

    override suspend fun deleteVacancy(vacancyEntity: VacancyEntity) {
        appDataBase.vacancyDao().deleteVacancy(vacancyEntity)
    }

    override suspend fun getFavouriteVacancy(): Flow<List<VacancyEntity>> {
        return appDataBase.vacancyDao().getFavouriteVacancy()
    }
}