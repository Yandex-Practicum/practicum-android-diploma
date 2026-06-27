package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return favoritesRepository.getFavoriteVacancies()
    }

    override suspend fun getVacancy(vacancyId: String): Vacancy? {
        return favoritesRepository.getVacancy(vacancyId)
    }

    override suspend fun addVacancy(vacancy: Vacancy) {
        favoritesRepository.addVacancy(vacancy)
    }

    override suspend fun removeVacancy(vacancyId: String) {
        favoritesRepository.removeVacancy(vacancyId)
    }

    override suspend fun isInFavorites(vacancyId: String): Boolean {
        return favoritesRepository.getVacancy(vacancyId) != null
    }
}
