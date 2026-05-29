package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.db.mapper.toDb
import ru.practicum.android.diploma.data.db.mapper.toVacancyDetail
import ru.practicum.android.diploma.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail
import kotlinx.coroutines.flow.map

class VacancyDbRepositoryImpl(
    val dao: FavoriteVacancyDao
) : VacancyDbRepository {

    override fun observeFavoriteVacancyById(id: String): Flow<VacancyDetail> =
        dao.observeFavoriteVacancy(id).map { entity ->
            entity.toVacancyDetail()
        }

    override fun observeFavoriteVacancies(): Flow<List<VacancyDetail>> =
        dao.observeFavoriteVacancies().map { entities ->
        entities.map { it.toVacancyDetail() }
    }


    override suspend fun addVacancyToFavorites(vacancy: VacancyDetail) {
        dao.addVacancy(vacancy.toDb())
    }

    override suspend fun deleteVacancyFromFavorites(vacancyId: String) {
        dao.deleteVacancy(vacancyId)
    }

    override suspend fun checkVacancyIsFavorite(vacancyId: String): Boolean {
        return dao.checkVacancyIsFavorite(vacancyId)
    }
}
