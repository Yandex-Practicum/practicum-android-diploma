package ru.practicum.android.diploma.favorite.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.details.data.db.FavoriteVacanciesDb
import ru.practicum.android.diploma.details.data.model.VacancyConverter
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class FavoriteRepositoryImpl@Inject constructor(favoriteVacanciesDb: FavoriteVacanciesDb, private val converter: VacancyConverter): FavoriteRepository {
    private val dao = favoriteVacanciesDb.getDao()
    override suspend fun getFavsVacancies(): Flow<List<Vacancy>> {
        return dao.getFavorites().map { converter.mapToVacancies(it) }
    }
}