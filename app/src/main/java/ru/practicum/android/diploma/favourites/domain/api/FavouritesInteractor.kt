package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

interface FavouritesInteractor {
    fun favouriteVacancies(): Flow<List<Vacancy>>
    fun favouriteIds(): Flow<List<Int>>
    suspend fun changeFavourite(vacancyFull: VacancyFull)
}
