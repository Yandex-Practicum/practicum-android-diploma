package ru.practicum.android.diploma.favorite.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.details.data.local.db.FavoriteVacanciesDb
import ru.practicum.android.diploma.favorite.domain.api.FavoriteRepository
import ru.practicum.android.diploma.search.data.network.converter.VacancyModelConverter
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    favoriteVacanciesDb: FavoriteVacanciesDb,
    private val converter: VacancyModelConverter
) : FavoriteRepository {
    
    private val dao = favoriteVacanciesDb.getDao()
    
    override suspend fun getFavsVacancies(): Flow<List<Vacancy>> {
        return dao.getFavorites().map { converter.mapToVacancies(it) }
    }

    override suspend fun removeVacancy(id: String): Flow<Int> {
        return flowOf( dao.delete(id))
    }
}