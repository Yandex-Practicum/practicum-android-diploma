package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun addToFavorite(vacancy: Vacancy) {
        repository.addToFavorite(vacancy)
    }

    override suspend fun delFromFavorite(vacancy: Vacancy) {
        repository.delFromFavorite(vacancy)
    }

    override fun getFavorites(): Flow<List<Vacancy>> {
        return repository.getFavorites()
    }

    override fun getFavoriteById(vacId: String): Flow<Vacancy> {
        return repository.getFavoriteById(vacId)
    }
}
