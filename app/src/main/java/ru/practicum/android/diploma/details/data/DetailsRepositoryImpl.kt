package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.details.data.db.FavoriteVacanciesDb
import ru.practicum.android.diploma.details.data.model.VacancyConverter
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    database: FavoriteVacanciesDb,
    private val converter: VacancyConverter
) : DetailsRepository {
    private val dao = database.getDao()

    override suspend fun removeVacancyFromFavorite(id: Long): Flow<Int> {
        return flowOf( dao.delete(id))
    }
    override suspend fun addVacancyToFavorite(vacancy: Vacancy): Flow<Long> {
        return flowOf( dao.insert(converter.toEntity(vacancy)))
    }
}