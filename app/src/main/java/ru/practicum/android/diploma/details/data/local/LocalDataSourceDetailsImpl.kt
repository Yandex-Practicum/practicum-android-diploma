package ru.practicum.android.diploma.details.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.practicum.android.diploma.details.data.local.db.FavoriteDao
import ru.practicum.android.diploma.details.data.local.model.VacancyConverter
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class LocalDataSourceDetailsImpl@Inject constructor(
    private val dao: FavoriteDao,
    private val converter: VacancyConverter
): LocalDataSource {
    
    override suspend fun removeVacancyFromFavorite(id: String): Flow<Int> {
        return flowOf(dao.delete(id))
    }
    override suspend fun addVacancyToFavorite(vacancy: Vacancy): Flow<Long> {
        return flowOf(dao.insert(converter.toEntity(vacancy)))
    }
}