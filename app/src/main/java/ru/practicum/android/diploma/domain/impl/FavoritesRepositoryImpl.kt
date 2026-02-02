package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.api.FavoritesRepository

class FavoritesRepositoryImpl(
    private val vacancyDao: VacancyDao
) : FavoritesRepository {

    override suspend fun addToFavorites(vacancy: VacancyEntity) {
        vacancyDao.insertVacancy(vacancy)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return vacancyDao.getVacancyById(id) != null
    }

    override suspend fun removeFromFavorites(id: String) {
        vacancyDao.deleteById(id)
    }

    override suspend fun getFavoriteById(id: String): VacancyEntity? {
        return vacancyDao.getVacancyById(id)
    }

    override suspend fun getAllFavorites(): List<VacancyEntity> {
        return vacancyDao.getAllFavorites()
    }
}
