package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.converters.toDatabaseEntity
import ru.practicum.android.diploma.data.converters.toModel
import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(private val vacancyDao: VacancyDao) : FavoritesRepository {
    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return vacancyDao.getFavoriteVacancies()
            .map { entities: List<VacancyEntity> ->
                entities.map { it.toModel() }
            }
    }

    override suspend fun getVacancy(vacancyId: String): Vacancy? {
        return vacancyDao.getVacancyById(vacancyId)?.toModel()
    }

    override suspend fun addVacancy(vacancy: Vacancy) {
        vacancyDao.insertVacancy(vacancy.toDatabaseEntity())
    }

    override suspend fun removeVacancy(vacancyId: String) {
        vacancyDao.deleteVacancy(vacancyId)
    }
}
