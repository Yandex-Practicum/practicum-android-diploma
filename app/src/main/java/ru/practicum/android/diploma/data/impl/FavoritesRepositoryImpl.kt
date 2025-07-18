package ru.practicum.android.diploma.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.DataBase
import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.data.db.mapToDetails
import ru.practicum.android.diploma.data.db.mapToPreview
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.repositories.FavoritesRepository

class FavoritesRepositoryImpl(private val dataBase: DataBase) : FavoritesRepository {

    override suspend fun insertVacancy(vacancy: VacancyEntity) {
        dataBase.vacanciesDao().insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(id: String) {
        dataBase.vacanciesDao().deleteVacancy(id)
    }

    override fun getAllVacancies(): Flow<List<VacanciesPreview>> =
        dataBase.vacanciesDao()
            .getAllVacancies()
            .map { entitiesList -> entitiesList.map { entity -> mapToPreview(entity) } }

    override suspend fun getVacancy(id: String): VacancyDetails =
        mapToDetails(dataBase.vacanciesDao().getVacancy(id))

    override suspend fun isFavorite(id: String): Boolean =
        dataBase.vacanciesDao().isFavorite(id)
}
