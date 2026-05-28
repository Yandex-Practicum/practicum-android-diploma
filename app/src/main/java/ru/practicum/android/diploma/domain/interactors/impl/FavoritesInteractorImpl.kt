package ru.practicum.android.diploma.domain.interactors.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository,
) : FavoritesInteractor {

    override suspend fun addToFavorites(vacancy: VacancyDetail) {
        repository.addToFavorites(vacancy)
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        repository.removeFromFavorites(vacancyId)
    }

    override fun getFavorites(): Flow<List<Vacancy>> {
        return repository.getFavorites()
    }

    override suspend fun getFavoriteById(vacancyId: String): VacancyDetail? {
        return repository.getFavoriteById(vacancyId)
    }

    override fun isFavorite(vacancyId: String): Flow<Boolean> {
        return repository.isFavorite(vacancyId)
    }
}
