package ru.practicum.android.diploma.favorites.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.VacancyItems

interface FavoriteInteractor {
    suspend fun getFavoritesList() : Flow<Pair<List<VacancyItems>?, String?>>
    suspend fun insertFavoriteVacancy(vacancy: VacancyItems)
}
