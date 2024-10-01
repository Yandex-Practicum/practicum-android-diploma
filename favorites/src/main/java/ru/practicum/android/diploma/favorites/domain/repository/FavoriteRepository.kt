package ru.practicum.android.diploma.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy

interface FavoriteRepository {

    fun getVacancies(): Flow<List<FavoriteVacancy>>

}
