package ru.practicum.android.diploma.data.vacancy

import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val vacancyDao: VacancyDao
) : FavoritesRepository {

    override suspend fun addToFavorites(vacancy: Vacancy) {
        vacancyDao.insertVacancy(vacancy.toEntity())
    }

    override suspend fun isFavorite(id: String): Boolean {
        return vacancyDao.getVacancyById(id) != null
    }

    override suspend fun removeFromFavorites(id: String) {
        vacancyDao.deleteById(id)
    }

    override suspend fun getFavoriteById(id: String): Vacancy? {
        return vacancyDao.getVacancyById(id)?.toDomain()
    }

    override suspend fun getAllFavorites(): List<Vacancy> {
        return vacancyDao.getAllFavorites().map { it.toDomain() }
    }
}
