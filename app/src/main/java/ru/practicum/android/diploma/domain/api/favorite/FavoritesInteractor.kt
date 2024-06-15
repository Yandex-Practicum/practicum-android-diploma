package ru.practicum.android.diploma.domain.api.favorite

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

interface FavoritesInteractor {
    suspend fun addVacancyToFavorites(vacancy: Vacancy)
    suspend fun getVacancyById(vacancyId: String): Vacancy
    suspend fun getFavoriteVacanciesPage(limit: Int, from: Int): VacancyPage
    suspend fun getAllFavorites(): List<Vacancy>
    suspend fun isVacancyFavorite(vacancyId: String): Boolean
    suspend fun removeVacancyFromFavorites(vacancy: Vacancy)
    suspend fun removeVacancyById(vacancyId: String)
}
