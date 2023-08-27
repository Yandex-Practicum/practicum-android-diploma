package ru.practicum.android.diploma.favorite.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class FavoritesInteractorImpl @Inject constructor() : FavoritesInteractor {
    override fun getFavorites(): Flow<List<Vacancy>> {
        return flow { emit(List(15) { Vacancy() }) }
    }

    override fun removeVacancy(id: Int) {

    }
}