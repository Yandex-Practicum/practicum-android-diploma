package ru.practicum.android.diploma.favorites.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.vacancy.domain.entity.VacancyFavorite

interface FavoriteInteractor {
    suspend fun getFavoritesList(): Flow<Pair<List<VacancyItems>?, String?>>
    suspend fun insertFavoriteVacancy(vacancy: VacancyFavorite)
    suspend fun deleteVacancyById(id: String)
    fun getVacancyById(id: String): Flow<VacancyFavorite?>
    suspend fun isVacancyFavorite(vacancyId: String): Boolean
}
