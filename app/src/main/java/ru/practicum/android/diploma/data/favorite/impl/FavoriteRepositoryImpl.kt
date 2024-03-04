package ru.practicum.android.diploma.data.favorite.impl

import android.util.Log
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
        vacancy.mapToVacancyDetailEntity().contactPhone?.let { Log.d("comment", it) }
        appDatabase.vacancyDtoDao().insertVacancy(vacancy.mapToVacancyDetailEntity())
    }

    override suspend fun deleteVacancy(vacancyId: Int) {
        appDatabase.vacancyDtoDao().deleteVacancy(vacancyId)
    }

    override suspend fun getAppIdVacancy(): List<String> {
        return appDatabase.vacancyDtoDao().getAllIdVacancy()
    }

    override suspend fun getVacancyId(id: String): VacancyDetail {
        return appDatabase.vacancyDtoDao().getVacancyId(id).mapToVacancyDetail()
    }

    override fun getListVacancy(): Flow<List<VacancyDetail>> = flow {
        val vacancy = appDatabase.vacancyDtoDao().getAllVacancy()
        emit(vacancy.mapToVacancyDetail())
    }
}
