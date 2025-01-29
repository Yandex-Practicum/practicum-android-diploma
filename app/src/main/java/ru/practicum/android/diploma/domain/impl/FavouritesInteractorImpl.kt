package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.domain.api.FavouritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouritesInteractorImpl(
    private val repository: FavouritesRepository
) : FavouritesInteractor {
    override suspend fun likeVacancy(vacancy: Vacancy) {
        repository.insertVacancy(vacancy)
    }

    override suspend fun dislikeVacancy(vacancy: Vacancy) {
        repository.deleteVacancy(vacancy)
    }

    override fun getFavouritesVacancy(): Flow<List<Vacancy>> {
        return repository.getVacancies()
    }

}
