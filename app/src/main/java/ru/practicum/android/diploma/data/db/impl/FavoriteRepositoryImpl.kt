package ru.practicum.android.diploma.data.db.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.converters.VacanciesDbConverter
import ru.practicum.android.diploma.data.db.dao.VacanciesDao
import ru.practicum.android.diploma.data.db.entity.VacanciesEntity
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails
import java.util.Date

class FavoriteRepositoryImpl(
    val vacancyDao: VacanciesDao,
    val converter: VacanciesDbConverter
) : FavoriteRepository {
    override suspend fun addToFavorite(vacancy: VacancyDetails) {
        val entity = converter.map(vacancy, Date())
        vacancyDao.insertToFavorite(entity)
    }

    override suspend fun delFromFavorite(vacancy: VacancyDetails) {
        val entity = converter.map(vacancy, Date())
        vacancyDao.deleteFromFavorite(entity)
    }

    override fun getFavorites(): Flow<List<VacancyDetails>> = flow {
        val vacancies = vacancyDao.getFavoritesVacancies()
        emit(convertFromEntities(vacancies))
    }

    override fun getFavoriteById(vacId: String): Flow<VacancyDetails?> = flow {
        val vacancy = vacancyDao.getFavoriteVacancieById(vacId)
        if (vacancy != null) {
            emit(converter.map(vacancy))
        } else {
            emit(null)
        }
    }

    private fun convertFromEntities(vacancies: List<VacanciesEntity>): List<VacancyDetails> {
        return vacancies.map { vacancy ->
            converter.map(vacancy)
        }
    }
}
