package ru.practicum.android.diploma.data.favorite.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDetailsDbConverter.mapToVacancyDetailDto
import ru.practicum.android.diploma.data.converters.VacancyDetailsDbConverter.mapToVacancyDetailDtoEntity
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository

class FavoriteRepositoryImpl(
    val appDatabase: AppDatabase
) : FavoriteRepository {

    override suspend fun addVacancy(vacancy: VacancyDetailDto) {
        appDatabase.vacancyDtoDao().insertVacancy(vacancy.mapToVacancyDetailDtoEntity())
    }

    override suspend fun deleteVacancy(vacancyId: Int) {
        appDatabase.vacancyDtoDao().deleteVacancy(vacancyId)
    }

    override fun getListVacancy(): Flow<List<VacancyDetailDto>> = flow {
        val vacancy = appDatabase.vacancyDtoDao().getAllTrack()
        emit(vacancy.mapToVacancyDetailDto())
    }
}
