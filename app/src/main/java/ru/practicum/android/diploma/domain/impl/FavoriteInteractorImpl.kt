package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun addToFavorite(vacancy: VacancyDetails) {
        repository.addToFavorite(vacancy)
    }

    override suspend fun delFromFavorite(vacancy: VacancyDetails) {
        repository.delFromFavorite(vacancy)
    }

    override fun getFavorites(): Flow<List<VacancyDetails>> {
        return repository.getFavorites()
    }

    override fun getFavoriteById(vacId: String): Flow<VacancyDetails?> {
        return repository.getFavoriteById(vacId)
    }
}
