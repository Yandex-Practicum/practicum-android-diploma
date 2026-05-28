package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.data.db.mapper.toDb
import ru.practicum.android.diploma.data.db.mapper.toVacancyDetail
import ru.practicum.android.diploma.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDbRepositoryImpl(
    val dao: FavoriteVacancyDao
) : VacancyDbRepository {

    override fun getFavoriteVacancies(): Flow<List<VacancyDetail>> = flow {
        val vacancies = dao.observeFVacancies()
        emit(convertFromFavoriteVacanciesEntity(vacancies))
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

    private fun convertFromFavoriteVacanciesEntity(vacancies: List<FavoriteVacancyEntity>): List<VacancyDetail> {
        return vacancies.map { vacancy -> vacancy.toVacancyDetail() }
    }
}
