package ru.practicum.android.diploma.favorites.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy

interface FavoriteInteractor {
    suspend fun getVacancies(): Flow<List<FavoriteVacancy>>
}
