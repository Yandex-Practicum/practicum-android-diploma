package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.api.FavoriteRepository
import ru.practicum.android.diploma.favorite.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class FavoritesInteractorImpl @Inject constructor(private val repository: FavoriteRepository) :
    FavoritesInteractor {
    override suspend fun getFavorites(): Flow<List<Vacancy>> {
        return repository.getFavsVacancies()
//        return flow { emit(List(15) { getMockVacancy() }) }
    }

//    override fun removeVacancy(id: Int) {
//
//    }
}