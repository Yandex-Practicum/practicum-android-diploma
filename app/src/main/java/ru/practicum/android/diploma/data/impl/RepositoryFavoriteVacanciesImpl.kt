package ru.practicum.android.diploma.data.impl

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.data.db.VacancyShortDbEntity
import ru.practicum.android.diploma.domain.models.main.LogoUrls
import ru.practicum.android.diploma.domain.models.main.Salary
import ru.practicum.android.diploma.domain.models.main.VacancyShort
import ru.practicum.android.diploma.domain.repositories.RepositoryFavoriteVacancies

class RepositoryFavoriteVacanciesImpl(
    private val vacancyDao: VacancyDao,
) : RepositoryFavoriteVacancies {

    override suspend fun insertVacancy(vacancy: VacancyShort): Result<Unit> {
        return try {
            val dataEntity = domainToData(vacancy)
            vacancyDao.insertVacancy(dataEntity)
            Result.success(Unit)
        } catch (e: SQLiteException) {
            Result.failure(e)
        }
    }

    override suspend fun getAllVacancies(): List<VacancyShort> {
        val vacanciesDataList = vacancyDao.getAll()
        val vacanciesDomainList = vacanciesDataList.map { dataToDomain(it) }
        return vacanciesDomainList
    }

    override suspend fun getById(vacancyId: Int): Result<VacancyShort> {
        return try {
            val vacancyData = vacancyDao.getById(vacancyId)
            if (vacancyData != null) {
                Result.success(dataToDomain(vacancyData))
            } else {
                Result.failure(Exception("Вакансия не найдена"))
            }
        } catch (e: SQLiteException) {
            Result.failure(e)
        }
    }

    override suspend fun deleteById(vacancyId: Int): Result<Unit> {
        return try {
            vacancyDao.deleteById(vacancyId)
            Result.success(Unit)
        } catch (e: SQLiteException) {
            Result.failure(e)
        }
    }

    override fun getVacanciesFlow(): Flow<List<VacancyShort>> {
        return vacancyDao.getAllFlow()
            .map { list -> list.map { dataToDomain(it) } }
    }

    override suspend fun clearDatabase(): Result<Unit> {
        return try {
            vacancyDao.clear()
            Result.success(Unit)
        } catch (e: SQLiteException) {
            Result.failure(e)
        }
    }

    private fun domainToData(vacancy: VacancyShort): VacancyShortDbEntity {
        return VacancyShortDbEntity(
            vacancyId = vacancy.vacancyId,
            logoUrl = vacancy.logoUrl?.original,
            name = vacancy.name,
            areaName = vacancy.area,
            employerName = vacancy.employer,
            salary = salaryToString(vacancy.salary),
            postedAt = vacancy.postedAt
        )
    }

    private fun dataToDomain(vacancy: VacancyShortDbEntity): VacancyShort {
        return VacancyShort(
            vacancyId = vacancy.vacancyId,
            logoUrl = LogoUrls(original = vacancy.logoUrl),
            name = vacancy.name,
            area = vacancy.areaName,
            employer = vacancy.employerName,
            salary = stringToSalary(vacancy.salary),
            postedAt = vacancy.postedAt,
        )
    }

    private fun salaryToString(input: Salary?): String {
        if (input == null) return "null*null*null*null"

        val from = input.from?.toString() ?: "null"
        val to = input.to?.toString() ?: "null"
        val currency = input.currency ?: "null"
        val gross = input.gross?.toString() ?: "null"

        return "$from*$to*$currency*$gross"
    }

    private fun stringToSalary(input: String): Salary {
        val parts = input.split("*")

        val from = parts.getOrNull(INDEX_FROM)?.takeIf { it != "null" }?.toIntOrNull()
        val to = parts.getOrNull(INDEX_TO)?.takeIf { it != "null" }?.toIntOrNull()
        val currency = parts.getOrNull(INDEX_CURRENCY)?.takeIf { it != "null" }
        val gross = parts.getOrNull(INDEX_GROSS)?.takeIf { it != "null" }?.toBooleanStrictOrNull()

        return Salary(from, to, currency, gross)
    }

    private companion object {
        const val INDEX_FROM = 0
        const val INDEX_TO = 1
        const val INDEX_CURRENCY = 2
        const val INDEX_GROSS = 3
    }
}
