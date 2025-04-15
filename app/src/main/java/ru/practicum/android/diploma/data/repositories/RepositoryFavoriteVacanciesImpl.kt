package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.data.db.VacancyShortDbEntity
import ru.practicum.android.diploma.domain.models.VacancyShortDmEntity
import ru.practicum.android.diploma.domain.repositories.RepositoryFavoriteVacancies

class RepositoryFavoriteVacanciesImpl(
    private val vacancyDao: VacancyDao,
) : RepositoryFavoriteVacancies {

    override suspend fun insertVacancy(vacancy: VacancyShortDmEntity): Result<Unit> {
        return try {
            val dataEntity = (domainToData(vacancy))
            vacancyDao.insertVacancy(dataEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllVacancies(): List<VacancyShortDmEntity> {
        val vacanciesDataList = vacancyDao.getAll()
        val vacanciesDomainList = vacanciesDataList.map { dataToDomain(it) }
        return vacanciesDomainList
    }

    override suspend fun getById(vacancyId: Int): Result<VacancyShortDmEntity> {
        return try {
            val vacancyData = vacancyDao.getById(vacancyId)
            if (vacancyData != null) {
                Result.success(dataToDomain(vacancyData))
            } else {
                Result.failure(Exception("Вакансия не найдена"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteById(vacancyId: Int): Result<Unit> {
        return try {
            vacancyDao.deleteById(vacancyId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getVacanciesFlow(): Flow<List<VacancyShortDmEntity>> {
        return vacancyDao.getAllFlow()
            .map { list -> list.map { dataToDomain(it) } }
    }

    override suspend fun clearDatabase(): Result<Unit> {
        return try {
            vacancyDao.clear()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun domainToData(vacancy: VacancyShortDmEntity): VacancyShortDbEntity {
        return VacancyShortDbEntity(
            vacancyId = vacancy.vacancyId,
            logoUrl = vacancy.logoUrl,
            name = vacancy.name,
            areaName = vacancy.areaName,
            employerName = vacancy.employerName,
            salary = vacancy.salary
        )
    }

    private fun dataToDomain(vacancy: VacancyShortDbEntity): VacancyShortDmEntity {
        return VacancyShortDmEntity(
            vacancyId = vacancy.vacancyId,
            logoUrl = vacancy.logoUrl,
            name = vacancy.name,
            areaName = vacancy.areaName,
            employerName = vacancy.employerName,
            salary = vacancy.salary ?: "Зарплата не указана"
        )
    }
}
