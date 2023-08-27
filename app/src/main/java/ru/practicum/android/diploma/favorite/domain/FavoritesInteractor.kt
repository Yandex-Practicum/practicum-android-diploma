package ru.practicum.android.diploma.favorite.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.Vacancy

interface FavoritesInteractor {
    fun getFavorites(): Flow<List<Vacancy>>
    fun removeVacancy(id: Int)
}