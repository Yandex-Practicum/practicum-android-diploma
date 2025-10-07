package ru.practicum.android.diploma.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.database.converters.AreaDbConverter
import ru.practicum.android.diploma.data.database.converters.EmployerDbConverter
import ru.practicum.android.diploma.data.database.converters.SalaryDbConverter
import ru.practicum.android.diploma.data.database.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.data.database.entity.AreaEntity
import ru.practicum.android.diploma.data.database.entity.EmployerEntity
import ru.practicum.android.diploma.data.database.entity.SalaryEntity
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val database: VacancyDao,
    private val converterVacancy: VacancyDbConverter,
    private val converterArea: AreaDbConverter,
    private val converterEmployer: EmployerDbConverter,
    private val converterSalary: SalaryDbConverter
) : FavoritesRepository {
    override suspend fun setVacancy(vacancy: Vacancy) {
        checkVacanciesInFavorite(vacancy.id.toInt()).collect {
            if (!it) {
                val area = vacancy.area?.let { converterArea.map(it) }
                val employer = vacancy.employer?.let { converterEmployer.map(it) }
                val salary = vacancy.salary?.let { converterSalary.map(it) }
                database.setVacancyTransaction(
                    vacancy = converterVacancy.map(
                        vacancy = vacancy,
                        area = area?.pk,
                        employer = employer?.pk,
                        salary = salary?.pk
                    ),
                    area = area,
                    employer = employer,
                    salary = salary
                )
            }
        }
    }

    override fun getVacancy(id: Int): Flow<Vacancy> {
        return flow {
            val vacancyEntity = database.getFavoritesVacancyById(id)
            if (vacancyEntity != null) {
                val areaEntity: AreaEntity? = vacancyEntity.area?.let { database.getFavoriteArea(it) }
                val employerEntity: EmployerEntity? = vacancyEntity.employer?.let { database.getFavoriteEmployer(it) }
                val salaryEntity: SalaryEntity? = vacancyEntity.salary?.let { database.getFavoriteSalary(it) }
                val vacancy: Vacancy = converterVacancy.map(
                    vacancyEntity = vacancyEntity,
                    salaryEntity = salaryEntity,
                    employerEntity = employerEntity,
                    areaEntity = areaEntity
                )
                emit(vacancy)
            }
        }
    }

    override fun getAllVacancies(): Flow<List<Vacancy>> {
        return flow {
            /*val listVacancy = converter.map(database.getAllFavoritesVacancies())
            emit(listVacancy)*/
        }
    }

    override fun checkVacanciesInFavorite(id: Int): Flow<Boolean> {
        return flow {
            val response = database.checkInFavorite(id = id)
            emit(response)
        }
    }

    override fun deleteVacancyFromFavorite(id: Int): Flow<Boolean> {
        return flow {
            val response = database.deleteVacancyFromFavorites(id)
            if (response == null || response == 0) {
                emit(false)
            } else {
                emit(true)
            }
        }
    }
}
