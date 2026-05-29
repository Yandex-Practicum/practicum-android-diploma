package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.db.mapper.toDb
import ru.practicum.android.diploma.data.db.mapper.toVacancyDetail
import ru.practicum.android.diploma.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDbRepositoryImpl(
    val dao: FavoriteVacancyDao
) : VacancyDbRepository {

    override fun observeFavoriteVacancyById(id: String): Flow<VacancyDetail> =
        dao.observeFavoriteVacancy(id)
            .map { entity -> entity.toVacancyDetail() }
            .distinctUntilChanged()

    override fun observeFavoriteVacancies(): Flow<List<VacancyDetail>> =
        dao.observeFavoriteVacancies()
            .map { entities -> entities.map { it.toVacancyDetail() } }
            .distinctUntilChanged()


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
