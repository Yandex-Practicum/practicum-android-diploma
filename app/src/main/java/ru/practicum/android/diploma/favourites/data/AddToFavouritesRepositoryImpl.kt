package ru.practicum.android.diploma.favourites.data

import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.data.db.AppDatabase
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity
import ru.practicum.android.diploma.favourites.domain.api.AddToFavouritesRepository

class AddToFavouritesRepositoryImpl(private val appDatabase: AppDatabase): AddToFavouritesRepository {
    override suspend fun checkVacancyInFavourites(vacancyId: Long): Boolean {
        val vacancy = appDatabase.vacancyDao().getVacancyById(vacancyId)
        if (vacancy.isNullOrEmpty()) {
            return false
        } else {
            return true
        }
    }

    override suspend fun addToFavourites(vacancy: DetailVacancy) {
        appDatabase.vacancyDao().addVacancyToFavorites(VacancyMapper.mapToFavoritesEntity(vacancy))
    }

    override suspend fun removeFromFavourites(vacancy: DetailVacancy) {
        appDatabase.vacancyDao().removeVacancyFromFavorites(VacancyMapper.mapToFavoritesEntity(vacancy))
    }
}
