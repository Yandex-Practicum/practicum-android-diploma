package ru.practicum.android.diploma.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.mappers.toEntity
import ru.practicum.android.diploma.data.mappers.toVacancy
import ru.practicum.android.diploma.data.mappers.toVacancyDetail
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoritesRepositoryImpl(
    private val vacancyDao: VacancyDao,
) : FavoritesRepository {

    override suspend fun addToFavorites(vacancy: VacancyDetail) {
        vacancyDao.insertVacancy(vacancy.toEntity())
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        vacancyDao.deleteVacancy(vacancyId)
    }

    override fun getFavorites(): Flow<List<Vacancy>> {
        return vacancyDao.getFavorites().map { entities ->
            entities.map { it.toVacancy() }
        }
    }

    override suspend fun getFavoriteById(vacancyId: String): VacancyDetail? {
        return vacancyDao.getFavoriteById(vacancyId)?.toVacancyDetail()
    }

    override fun isFavorite(vacancyId: String): Flow<Boolean> {
        return vacancyDao.isFavorite(vacancyId)
    }
}
