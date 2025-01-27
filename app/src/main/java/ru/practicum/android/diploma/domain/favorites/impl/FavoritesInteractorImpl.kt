package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {
    override fun getFavorites(): Flow<Resource<List<Vacancy>>> = flow {
        favoritesRepository.getFavorites()
    }

    override suspend fun getFavoritesById(vacancyId: Long): Boolean =
        favoritesRepository.getFavoritesById(vacancyId)

    override suspend fun saveVacancy(vacancy: Vacancy) {
        favoritesRepository.saveVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        favoritesRepository.deleteVacancy(vacancy)
    }

    override suspend fun removeVacancyById(vacancyId: Long) {
        favoritesRepository.removeVacancyById(vacancyId)
    }
}
