package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converters.VacancyEntityConverter
import ru.practicum.android.diploma.domain.api.favorite.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage
import kotlin.math.ceil

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: VacancyEntityConverter
) : FavoritesRepository {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDao().addVacancy(converter.map(vacancy))
    }

    override suspend fun getVacancyById(vacancyId: String): Vacancy {
        return converter.map(appDatabase.favoritesDao().getVacancyDetailsById(vacancyId))
    }

    override suspend fun getFavoriteVacanciesPage(limit: Int, from: Int): VacancyPage {
        val vacancyList = appDatabase.favoritesDao().getFavoritesPage(limit, from).map { converter.map(it) }
        val countFavoriteVacancies = appDatabase.favoritesDao().favoriteCount()
        val fromPages = ceil(countFavoriteVacancies * 1.0 / limit).toInt()
        val currPage = ceil(from * 1.0 / limit).toInt()
        return VacancyPage(vacancyList, currPage, fromPages, countFavoriteVacancies)
    }

    override suspend fun getAllFavorites(): List<Vacancy> {
        return appDatabase.favoritesDao().getAllFavorites().map { converter.map(it) }
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDao().removeVacancy(converter.map(vacancy))
    }

    override suspend fun removeVacancyById(vacancyId: String) {
        appDatabase.favoritesDao().removeVacancyById(vacancyId)
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return appDatabase.favoritesDao().isVacancyFavorite(vacancyId) != 0
    }
}
