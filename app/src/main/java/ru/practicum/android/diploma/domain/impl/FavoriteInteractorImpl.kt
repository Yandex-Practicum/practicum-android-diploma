package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetail

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun addToFavorite(vacancy: VacancyDetail) {
        repository.addToFavorite(vacancy)
    }

    override suspend fun delFromFavorite(vacancy: VacancyDetail) {
        repository.delFromFavorite(vacancy)
    }

    override fun getFavorites(): Flow<List<VacancyDetail>> {
        return repository.getFavorites()
    }

    override fun getFavoriteById(vacId: String): Flow<VacancyDetail> {
        return repository.getFavoriteById(vacId)
    }
}
