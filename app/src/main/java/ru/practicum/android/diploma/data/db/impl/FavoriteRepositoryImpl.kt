package ru.practicum.android.diploma.data.db.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converters.VacanciesDbConverter
import ru.practicum.android.diploma.data.db.entity.VacanciesEntity
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteRepositoryImpl(
    val appDatabase: AppDatabase,
    val converter: VacanciesDbConverter
) : FavoriteRepository {
    override suspend fun addToFavorite(vacancy: Vacancy) {
        val entity = converter.map(vacancy)
        appDatabase.vacanciesDao().insertToFavorite(entity)
    }

    override suspend fun delFromFavorite(vacancy: Vacancy) {
        val entity = converter.map(vacancy)
        appDatabase.vacanciesDao().deleteFromFavorite(entity)
    }

    override fun getFavorites(): Flow<List<Vacancy>> = flow {
        val vacancies = appDatabase.vacanciesDao().getFavoritesVacancies()
        emit(convertFromEntities(vacancies))
    }

    override fun getFavoriteById(vacId: String): Flow<Vacancy> = flow {
        val vacancy = appDatabase.vacanciesDao().getFavoriteVacancieById(vacId)
        emit(converter.map(vacancy))
    }

    private fun convertFromEntities(vacancies: List<VacanciesEntity>): List<Vacancy> {
        return vacancies.map { vacancy ->
            converter.map(vacancy)
        }
    }
}
