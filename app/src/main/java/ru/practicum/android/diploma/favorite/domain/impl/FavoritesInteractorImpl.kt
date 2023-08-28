package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.favorite.domain.FavoritesInteractor
import ru.practicum.android.diploma.search.domain.Vacancy
import ru.practicum.android.diploma.util.getMockVacancy
import javax.inject.Inject

class FavoritesInteractorImpl @Inject constructor(private val repository: FavoriteRepository) : FavoritesInteractor {
    override suspend fun getFavorites(): Flow<List<Vacancy>> {
        return repository.getFavsVacancies()
//        return flow { emit(List(15) { getMockVacancy() }) }
    }

//    override fun removeVacancy(id: Int) {
//
//    }
}