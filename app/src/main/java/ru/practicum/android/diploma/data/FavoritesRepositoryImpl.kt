package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.db.converters.VacancyEntityConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: VacancyEntityConverter
) : FavoritesRepository {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDao().addVacancy(converter.map(vacancy))
    }

    override suspend fun getFavoriteVacancies(limit: Int, from: Int): List<Vacancy> {
        return appDatabase.favoritesDao().getFavoritesPage(limit, from).map { converter.map(it) }
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDao().removeVacancy(converter.map(vacancy))
    }

    override suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean {
        return appDatabase.favoritesDao().isVacancyFavorite(vacancy.id) != 0
    }
}
