package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.FavouritesInteractor
import ru.practicum.android.diploma.domain.FavouritesRepository

class FavoruitesInteractorImpl(
    private val repository: FavouritesRepository
) : FavouritesInteractor {
    override fun likeVacancy(vacancy: Vacancy) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertVacancy(vacancy)
        }
    }

    override fun dislikeVacancy(vacancy: Vacancy) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteVacancy(vacancy)
        }
    }

    override suspend fun getFavouritesVacancy(): Flow<List<Vacancy>> {
        return repository.getVacancies()
    }

}
