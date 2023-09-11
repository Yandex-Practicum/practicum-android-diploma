package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.data.local.LocalDataSource
import ru.practicum.android.diploma.details.domain.DetailsLocalRepository
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import javax.inject.Inject

class DetailsLocalRepositoryImpl@Inject constructor(
    private val localDataSource: LocalDataSource,
) : DetailsLocalRepository {
    override suspend fun removeVacancyFromFavorite(id: String): Flow<Int> {
        return localDataSource.removeVacancyFromFavorite(id)
    }

    override suspend fun addVacancyToFavorite(vacancy: VacancyFullInfo): Flow<Unit> {
        return localDataSource.addVacancyToFavorite(vacancy)
    }

    override suspend fun showIfInFavourite(id: String): Flow<Boolean> {
        return localDataSource.showIfInFavouriteById(id)
    }
}