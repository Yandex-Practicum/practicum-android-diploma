package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.api.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesVacancyInteractorImpl(
    private val favoritesVacancyRepository: FavoritesVacancyRepository
) : FavoritesVacancyInteractor {
    override suspend fun getAllFavoritesVacancy(): Flow<List<Vacancy>> {
        return favoritesVacancyRepository.getAllFavoritesVacancy()
    }

    override suspend fun getOneFavoriteVacancy(vacancyId: String): Vacancy {
        return favoritesVacancyRepository.getOneFavoriteVacancy(vacancyId)
    }

    override suspend fun deleteFavoriteVacancy(vacancy: FavoriteVacancyEntity): Int {
        return favoritesVacancyRepository.deleteFavoriteVacancy(vacancy)
    }
}
