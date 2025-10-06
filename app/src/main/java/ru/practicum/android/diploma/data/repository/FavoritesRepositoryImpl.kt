package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.database.converters.AreaDbConverter
import ru.practicum.android.diploma.data.database.converters.EmployerDbConverter
import ru.practicum.android.diploma.data.database.converters.SalaryDbConverter
import ru.practicum.android.diploma.data.database.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.database.dao.VacancyDao
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
        val area =  vacancy.area?.let { converterArea.map(it) }
        val employer =  vacancy.employer?.let { converterEmployer.map(it) }
        val salary =  vacancy.salary?.let { converterSalary.map(it) }
        /*database.setVacancyTransaction(
            vacancy = converterVacancy.map(vacancy = vacancy, area = area?.pk, employer = employer?.pk, salary = salary?.pk),
            area = area,
            employer = employer,
            salary = salary
        )*/
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
