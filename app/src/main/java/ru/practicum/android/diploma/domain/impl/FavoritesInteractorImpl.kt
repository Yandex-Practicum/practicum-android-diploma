package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository
) : FavoritesInteractor {
    override suspend fun setVacancy(vacancy: Vacancy) {
        repository.setVacancy(vacancy = vacancy)
    }

    override fun getAllVacancies(): Flow<List<Vacancy>> {
        return repository.getAllVacancies()
    }

    override fun checkVacancyInFavorite(id: String): Flow<Boolean> {
        return repository.checkVacanciesInFavorite(id)
    }
}
