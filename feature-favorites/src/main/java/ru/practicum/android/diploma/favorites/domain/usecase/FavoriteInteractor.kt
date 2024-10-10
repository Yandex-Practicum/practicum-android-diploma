package ru.practicum.android.diploma.favorites.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy

internal interface FavoriteInteractor {
    suspend fun getVacancies(): Flow<List<FavoriteVacancy>>

    suspend fun getVacanciesNumber(): Flow<Pair<Int?, String?>>

    suspend fun getVacanciesPaginated(limit: Int, offset: Int): Flow<Pair<List<FavoriteVacancy>?, String?>>
}
