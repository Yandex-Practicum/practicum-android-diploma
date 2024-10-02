package ru.practicum.android.diploma.favorites.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy

interface FavoriteRepository {

    suspend fun getVacancies(): Flow<List<FavoriteVacancy>>

    suspend fun getVacanciesNumber(): Flow<Resource<Int>>

    suspend fun getVacanciesPaginated(limit: Int, offset: Int): Flow<Resource<List<FavoriteVacancy>>>

}
