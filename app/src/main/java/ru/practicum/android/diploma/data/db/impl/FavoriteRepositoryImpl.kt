package ru.practicum.android.diploma.data.db.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.converters.VacanciesDbConverter
import ru.practicum.android.diploma.data.db.dao.VacanciesDao
import ru.practicum.android.diploma.data.db.entity.VacanciesEntity
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteRepositoryImpl(
    val vacancyDao: VacanciesDao,
    val converter: VacanciesDbConverter
) : FavoriteRepository {
    override suspend fun addToFavorite(vacancy: Vacancy) {
        val entity = converter.map(vacancy)
        vacancyDao.insertToFavorite(entity)
    }

    override suspend fun delFromFavorite(vacancy: Vacancy) {
        val entity = converter.map(vacancy)
        vacancyDao.deleteFromFavorite(entity)
    }

    override fun getFavorites(): Flow<List<Vacancy>> = flow {
        val vacancies = vacancyDao.getFavoritesVacancies()
        emit(convertFromEntities(vacancies))
    }

    override fun getFavoriteById(vacId: String): Flow<Vacancy> = flow {
        val vacancy = vacancyDao.getFavoriteVacancieById(vacId)
        emit(converter.map(vacancy))
    }

    private fun convertFromEntities(vacancies: List<VacanciesEntity>): List<Vacancy> {
        return vacancies.map { vacancy ->
            converter.map(vacancy)
        }
    }
}
