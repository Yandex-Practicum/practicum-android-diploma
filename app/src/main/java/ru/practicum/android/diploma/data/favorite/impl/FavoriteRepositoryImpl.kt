package ru.practicum.android.diploma.data.favorite.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDetailDbConverter.mapToVacancyDetail
import ru.practicum.android.diploma.data.converters.VacancyDetailDbConverter.mapToVacancyDetailEntity
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

class FavoriteRepositoryImpl(
    val appDatabase: AppDatabase
) : FavoriteRepository {

    override suspend fun addVacancy(vacancy: VacancyDetail) {
        appDatabase.vacancyDtoDao().insertVacancy(vacancy.mapToVacancyDetailEntity())
    }

    override suspend fun deleteVacancy(vacancyId: Int) {
        appDatabase.vacancyDtoDao().deleteVacancy(vacancyId)
    }

    override fun getListVacancy(): Flow<List<VacancyDetail>> = flow {
        val vacancy = appDatabase.vacancyDtoDao().getAllTrack()
        emit(vacancy.mapToVacancyDetail())
    }
}
