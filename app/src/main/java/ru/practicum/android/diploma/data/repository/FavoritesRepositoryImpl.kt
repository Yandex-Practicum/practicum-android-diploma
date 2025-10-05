package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.database.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val database: VacancyDao,
    private val converter: VacancyDbConverter
) : FavoritesRepository {
    override suspend fun setVacancy(vacancy: Vacancy) {
        val vacancyEntity = converter.map(vacancy = vacancy)
        database.setFavoriteVacancy(vacancyEntity)
    }

    override fun getAllVacancies(): Flow<List<Vacancy>> {
        return flow {
            val listVacancy = converter.map(database.getAllFavoritesVacancies())
            emit(listVacancy)
        }
    }

    override fun checkVacanciesInFavorite(id: String): Flow<Boolean> {
        return flow {
            val response = database.checkInFavorite(id = id)
            emit(response)
        }
    }
}
